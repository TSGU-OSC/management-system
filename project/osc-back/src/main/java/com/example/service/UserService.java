package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.entity.User;
import com.example.model.entity.VerifyCodeEntity;
import com.example.model.vo.UserAddVO;
import com.example.model.vo.UserLoginVO;
import jakarta.servlet.http.HttpServletRequest;


/**
 * 用户服务
 *
 * @author lwy
 */
public interface UserService extends IService<User> {


    /**
     * 添加用户
     *
     * @return
     */
    long addUser(UserAddVO userAddVO);

    /**
     * 用户登录
     *
     * @return 脱敏后的用户信息
     */
    User userLogin(UserLoginVO userLoginVO, HttpServletRequest request);

    /**
     * 用户登出
     */
    void userLogout(HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param originUser 脱敏前的用户
     * @return 脱敏后用户信息
     */
    User getSafetyUser(User originUser);

    /**
     * 获取验证码
     *
     * @return
     */
    VerifyCodeEntity generateVerifyCode();

    /**
     * 检验验证码
     *
     * @param userLoginVO
     * @return
     */
    Boolean verifyVerifyCode(UserLoginVO userLoginVO);
}
