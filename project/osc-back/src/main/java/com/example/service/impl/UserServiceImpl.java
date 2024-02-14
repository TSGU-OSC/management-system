package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.BaseContext;
import com.example.enums.ErrorCodeEnum;
import com.example.exception.BusinessException;
import com.example.mapper.NewUserMapper;
import com.example.model.dto.QueryDTO;
import com.example.model.dto.UserAddDTO;
import com.example.model.dto.UserLoginDTO;
import com.example.model.dto.VerifyCodeDTO;
import com.example.model.entity.User;
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

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.example.constant.UserConstant.*;

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
     * 添加用户
     *
     * @param userAddDTO 用户添加DTO类
     * @return 添加的用户id
     */
    @Override
    public long addUser(UserAddDTO userAddDTO) {
        // 学号不可重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", userAddDTO.getCode());
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCodeEnum.PARAMS_ERROR, "学号重复");
        }
        // 加密密码
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userAddDTO.getPassword()).getBytes());
        // 建立用户实体
        User user = new User();
        user.setCode(userAddDTO.getCode());
        user.setPassword(encryptPassword);
        user.setName(userAddDTO.getName());
        user.setGender(userAddDTO.getGender());
        user.setClazz(userAddDTO.getClazz());
        user.setMajor(userAddDTO.getMajor());
        user.setAcademy(userAddDTO.getAcademy());
        user.setDuty(userAddDTO.getDuty());
        user.setRole(userAddDTO.getRole());
        // 存储用户信息
        this.save(user);
        // 用户添加成功
        return user.getId();
    }

    /**
     * 用户登录
     *
     * @param userLoginDTO
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
     * 查询用户
     *
     * @param queryDTO 查询用户DTO类
     * @return 用户列表
     */
    @Override
    public List<User> listUsers(QueryDTO queryDTO) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 非空查询
        lambdaQueryWrapper.like(org.apache.commons.lang3.StringUtils.isNotEmpty(queryDTO.getCode()), User::getCode, queryDTO.getCode())
                .like(org.apache.commons.lang3.StringUtils.isNotEmpty(queryDTO.getMajor()), User::getMajor, queryDTO.getMajor())
                .like(org.apache.commons.lang3.StringUtils.isNotEmpty(queryDTO.getName()), User::getName, queryDTO.getName())
                .like(org.apache.commons.lang3.StringUtils.isNotEmpty(queryDTO.getCity()), User::getCity, queryDTO.getCity())
                .like(org.apache.commons.lang3.StringUtils.isNotEmpty(queryDTO.getAcademy()), User::getAcademy, queryDTO.getAcademy())
                .like(org.apache.commons.lang3.StringUtils.isNotEmpty(queryDTO.getPhone()), User::getPhone, queryDTO.getPhone())
                .like(org.apache.commons.lang3.StringUtils.isNotEmpty(queryDTO.getProvince()), User::getProvince, queryDTO.getProvince())
                .like(queryDTO.getDuty() != null, User::getDuty, queryDTO.getDuty())
                .like(queryDTO.getClazz() != null, User::getClazz, queryDTO.getClazz())
                .like(queryDTO.getStatus() != null, User::getStatus, queryDTO.getStatus())
                .like(queryDTO.getRole() != null, User::getRole, queryDTO.getRole());

        List<User> list = this.list(lambdaQueryWrapper);
        // 如果用户权限低，对查询到的用户进行脱敏
        Long currentId = BaseContext.getCurrentId();
        if (this.getById(currentId).getRole() == DEFAULT_USER) {
            list = list.stream().map(this::getSafetyUser).toList();
        }
        return list;
    }

    /**
     * 更改用户
     *
     * @param user 需更改的用户
     * @return 更改后的用户
     */
    @Override
    public User updateUser(User user) {
        // 获取当前登录用户
        Long currentUserId = BaseContext.getCurrentId();
        User currentUser = this.getById(currentUserId);
        // 学号不能修改
        if (!Objects.equals(user.getCode(), this.getById(user.getId()).getCode())) {
            throw new BusinessException(ErrorCodeEnum.NO_AUTH, "学号不可修改");
        }
        // 如果是修改自己的信息
        if (currentUserId.equals(user.getId())) {
            // 超级管理员才能修改自己的 role status duty
            if (currentUser.getRole() != SUPER_ADMIN_USER &&
                    (!currentUser.getRole().equals(user.getRole()) ||
                            !currentUser.getStatus().equals(user.getStatus()) ||
                            !currentUser.getDuty().equals(user.getDuty()))) {
                throw new BusinessException(ErrorCodeEnum.NO_AUTH, "权限不足");
            }
        } else {  // 如果不是修改自己的信息
            // 超管才能修改他人的 role status duty ，除非修改者的权限高于被修改者，
            // 并且修改者不能将被修改者的权限改至高于等于修改者的权限
            if (currentUser.getRole() != SUPER_ADMIN_USER &&
                    currentUser.getRole() <=
                            (Math.max(user.getRole(), this.getById(user.getId()).getRole()))) {
                throw new BusinessException(ErrorCodeEnum.NO_AUTH, "权限不足");
            }
        }
        // 权限足够，可以修改
        this.updateById(user);
        // 获取更改后的用户
        return this.getById(user.getId());
    }

    /**
     * 用户脱敏
     *
     * @param originUser 脱敏前的用户
     * @return 脱敏后用户信息
     */
    @Override
    public User getSafetyUser(User originUser) {
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

        // 存入Redis，设置120s过期

        redisCache.setCacheObject(verifyCodeKey, verifyCode, 120, TimeUnit.SECONDS);

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