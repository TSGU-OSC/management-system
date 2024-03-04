package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enums.ErrorCodeEnum;
import com.example.exception.BusinessException;
import com.example.mapper.UserMapper;
import com.example.model.dto.UserLoginDTO;
import com.example.model.dto.VerifyCodeDTO;
import com.example.model.entity.User;
import com.example.service.LoginService;
import com.example.service.UserService;
import com.example.utils.RedisCache;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.example.constant.UserConstant.SALT;

/**
 * 用户登录服务实现类
 *
 * @author lwy
 */
@Service
@Slf4j
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements LoginService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisCache redisCache;
    @Resource
    UserService userService;


    /**
     * 用户登录
     *
     * @param userLoginDTO 用户登录DTO类
     * @return 脱敏后的用户信息
     */
    @Override
    public User userLogin(UserLoginDTO userLoginDTO, HttpServletRequest request) {
        // 校验验证码
        this.verifyVerifyCode(userLoginDTO);
        // 对密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userLoginDTO.getPassword()).getBytes());
        // 尝试登录
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", userLoginDTO.getCode());
        queryWrapper.eq("password", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        //用户不存在
        if (user == null) {
            log.info("在数据库中找不到学号和密码匹配的数据");
            throw new BusinessException(ErrorCodeEnum.PARAMS_ERROR, "用户不存在");
        }
        // 用户被封号
        if (user.getStatus() == 1) {
            log.info("用户已被封号");
            throw new BusinessException(ErrorCodeEnum.PARAMS_ERROR, "用户已被封号");
        }
        //用户脱敏
        User safetyUser = userService.getSafetyUser(user);
        // 设置当前登录用户
        request.getSession().setAttribute("USER_LOGIN_STATE", user.getId());

        return safetyUser;
    }

    /**
     * 用户登出
     */
    @Override
    public void userLogout(HttpServletRequest request) {
        // 注销session
        request.getSession().invalidate();
    }

    /**
     * 生产验证码
     *
     * @return 验证码DTO类
     */
    @Override
    public VerifyCodeDTO generateVerifyCode() {
        // 创建验证码对象
        Captcha captcha = new ArithmeticCaptcha();
        // 生成验证码编号
        String verifyCodeKey = UUID.randomUUID().toString();
        String verifyCode = captcha.text();
        // 获取验证码图片，构造响应结果
        VerifyCodeDTO verifyCodeDTO = new VerifyCodeDTO(verifyCodeKey, captcha.toBase64(), verifyCode);
        // 存入Redis，设置30s过期
        redisCache.setCacheObject(verifyCodeKey, verifyCode, 30, TimeUnit.SECONDS);

        return verifyCodeDTO;
    }

    /**
     * 检验验证码
     *
     * @param userLoginDTO 用户登录DTO类
     */
    @Override
    public void verifyVerifyCode(UserLoginDTO userLoginDTO) {
        // 获取用户输入的验证码
        String actual = userLoginDTO.getVerifyCode();
        // 判断验证码是否过期
        if (redisCache.getCacheExpire(userLoginDTO.getVerifyCodeKey(), TimeUnit.SECONDS) < 0) {
            throw new BusinessException(ErrorCodeEnum.PARAMS_ERROR, "验证码过期");
        }
        // 从redis读取验证码并删除缓存
        String expect = redisCache.getCacheObject(userLoginDTO.getVerifyCodeKey());
        redisCache.deleteCacheObject(userLoginDTO.getVerifyCodeKey());
        // 比较用户输入的验证码和缓存中的验证码是否一致，不一致则抛错
        if (!StringUtils.hasText(expect) || !StringUtils.hasText(actual) || !actual.equalsIgnoreCase(expect)) {
            throw new BusinessException(ErrorCodeEnum.PARAMS_ERROR, "验证码错误");
        }
    }
}