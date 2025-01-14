package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.constant.UserConstant;
import com.example.enums.ErrorCodeEnum;
import com.example.exception.BusinessException;
import com.example.mapper.UserMapper;
import com.example.model.dto.RegisterDTO;
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



/**
 * 用户登录服务实现类
 *
 * @author osc
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
//        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userLoginDTO.getPassword()).getBytes());
        // 尝试登录
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", userLoginDTO.getCode());
        queryWrapper.eq("password", userLoginDTO.getPassword());
        User user = userMapper.selectOne(queryWrapper);
        //用户不存在
        if (user == null) {
            log.info("在数据库中找不到学号和密码匹配的数据");
            throw new BusinessException(ErrorCodeEnum.PARAMS_ERROR, "用户名或密码错误");
        }
        // 用户被封号
        if (user.getStatus() == 1) {
            log.info("用户已被封号");
            throw new BusinessException(ErrorCodeEnum.PARAMS_ERROR, "用户已被封号");
        }
        if (user.getStatus() == 2) {
            log.info("注册用户需要得到管理员同意");
            throw new BusinessException(ErrorCodeEnum.PARAMS_ERROR, "新注册用户需要得到管理员同意才能登录");
        }
        //用户脱敏
//        User safetyUser = userService.getSafetyUser(user);
        // 设置当前登录用户
        request.getSession().setAttribute("USER_LOGIN_STATE", user.getId());

        return user;
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
        redisCache.setCacheObject(verifyCodeKey, verifyCode, UserConstant.CODE_EXPIRE_TIME, TimeUnit.SECONDS);

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

    @Override
    public long register(RegisterDTO registerDTO) {
        // 学号不可重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", registerDTO.getCode());
        queryWrapper.eq("is_deleted",0);
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCodeEnum.PARAMS_ERROR, "学号重复");
        }
        // 设置默认密码
        if (org.apache.commons.lang3.StringUtils.isBlank(registerDTO.getPassword())) {
            registerDTO.setPassword("12345678");
        }
        // 加密密码
//        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + registerDTO.getPassword()).getBytes());
        // 建立用户实体
        User user = new User();
        user.setCode(registerDTO.getCode());
        user.setPassword(registerDTO.getPassword());
        user.setName(registerDTO.getName());
        user.setGender(registerDTO.getGender());
        user.setClazz(registerDTO.getClazz());
        user.setProvince(registerDTO.getProvince());
        user.setCity(registerDTO.getCity());
        user.setMajor(registerDTO.getMajor());
        user.setIdCard(registerDTO.getIdCard());
        user.setPhone(registerDTO.getPhone());
        user.setIntroduction(registerDTO.getIntroduction());
        user.setAcademy(registerDTO.getAcademy());
        user.setDepartment(registerDTO.getDepartment());
        user.setStatus(2);
//        user.setCreateUser(0L);
//        user.setUpdateUser(0L);
        // 存储用户信息
        this.save(user);
        // 用户添加成功
        return user.getId();
    }
}