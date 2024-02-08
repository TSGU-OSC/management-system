package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.ErrorCode;
import com.example.exception.BusinessException;
import com.example.mapper.NewUserMapper;
import com.example.model.entity.User;
import com.example.model.entity.VerifyCodeEntity;
import com.example.model.vo.UserAddVO;
import com.example.model.vo.UserLoginVO;
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
 * 用户服务实现类
 *
 * @author lwy
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<NewUserMapper, User> implements UserService {

    @Resource
    private NewUserMapper userMapper;

    @Resource
    private RedisCache redisCache;

    /**
     * 对密码进行加盐加密（加盐就是让密码加密后更复杂）
     */
    private static final String SALT = "lwy";

    /**
     * 添加用户
     *
     * @param userAddVO
     * @return 添加的用户id
     */
    @Override
    public long addUser(UserAddVO userAddVO) {
        if (userAddVO == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "参数为空");
        }
        // 学号不可重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", userAddVO.getCode());
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "学号重复");
        }
        // 加密密码
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userAddVO.getPassword()).getBytes());
        // 建立用户实体
        User user = new User();
        user.setCode(userAddVO.getCode());
        user.setPassword(encryptPassword);
        user.setName(userAddVO.getName());
        user.setGender(userAddVO.getGender());
        user.setClazz(userAddVO.getClazz());
        user.setMajor(userAddVO.getMajor());
        user.setAcademy(userAddVO.getAcademy());
        user.setDuty(userAddVO.getDuty());
        user.setRole(userAddVO.getRole());
        // 存储用户信息
        boolean result = this.save(user);
        // 添加失败
        if (!result) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "注册失败");
        }
        // 用户添加成功
        return user.getId();
    }

    /**
     * 用户登录
     *
     * @param userLoginVO
     * @return 脱敏后的用户信息
     */
    @Override
    public User userLogin(UserLoginVO userLoginVO, HttpServletRequest request) {
        // 校验验证码
        this.verifyVerifyCode(userLoginVO);
        // 对密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userLoginVO.getPassword()).getBytes());
        // 尝试登录
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", userLoginVO.getCode());
        queryWrapper.eq("password", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        //用户不存在
        if (user == null) {
            log.info("在数据库中找不到学号和密码匹配的数据");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }
        // 用户被封号
        if (user.getStatus() == 1) {
            log.info("用户已被封号");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户已被封号");
        }
        //用户脱敏
        User safetyUser = getSafetyUser(user);
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
     * 用户脱敏
     *
     * @param originUser 脱敏前的用户
     * @return 脱敏后用户信息
     */
    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户为空");
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setCode(originUser.getCode());
        safetyUser.setAvator(originUser.getAvator());
        safetyUser.setName(originUser.getName());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setProvince(originUser.getProvince());
        safetyUser.setCity(originUser.getCity());
        safetyUser.setClazz(originUser.getClazz());
        safetyUser.setMajor(originUser.getMajor());
        safetyUser.setAcademy(originUser.getAcademy());
        safetyUser.setDuty(originUser.getDuty());
        safetyUser.setIntroduction(originUser.getIntroduction());

        return safetyUser;
    }

    /**
     * 生产验证码
     *
     * @return
     */
    @Override
    public VerifyCodeEntity generateVerifyCode(){
        // 创建验证码对象
        Captcha captcha = new ArithmeticCaptcha();

        // 生成验证码编号
        String verifyCodeKey = UUID.randomUUID().toString();
        String verifyCode = captcha.text();

        // 获取验证码图片，构造响应结果
        VerifyCodeEntity verifyCodeEntity = new VerifyCodeEntity(verifyCodeKey, captcha.toBase64(), verifyCode);

        // 存入Redis，设置120s过期

        redisCache.setCacheObject(verifyCodeKey, verifyCode, 120, TimeUnit.SECONDS);

        return verifyCodeEntity;
    }

    /**
     * 检验验证码
     *
     * @param userLoginVO
     * @return
     */
    @Override
    public Boolean verifyVerifyCode(UserLoginVO userLoginVO) {
        // 校验验证码
        // 获取用户输入的验证码
        String actual = userLoginVO.getVerifyCode();
        // 判断验证码是否过期
        if (redisCache.getCacheExpire(userLoginVO.getVerifyCodeKey(), TimeUnit.SECONDS) < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"验证码过期");
        }
        // 从redis读取验证码并删除缓存
        String expect = redisCache.getCacheObject(userLoginVO.getVerifyCodeKey());
        redisCache.deleteCacheObject(userLoginVO.getVerifyCodeKey());

        // 比较用户输入的验证码和缓存中的验证码是否一致，不一致则抛错
        if (!StringUtils.hasText(expect) || !StringUtils.hasText(actual) || !actual.equalsIgnoreCase(expect)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"验证码错误");
        }

        return true;
    }
}