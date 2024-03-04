package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.dto.UserLoginDTO;
import com.example.model.dto.VerifyCodeDTO;
import com.example.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 用户登录服务类
 *
 * @author lwy
 */
public interface LoginService extends IService<User> {
    /**
     * 用户登录
     *
     * @return 脱敏后的用户信息
     */
    User userLogin(UserLoginDTO userLoginDTO, HttpServletRequest request);

    /**
     * 用户登出
     */
    void userLogout(HttpServletRequest request);

    /**
     * 获取验证码
     *
     * @return 验证码DTO类
     */
    VerifyCodeDTO generateVerifyCode();

    /**
     * 检验验证码
     *
     * @param userLoginDTO 用户登录DTO类
     */
    void verifyVerifyCode(UserLoginDTO userLoginDTO);
}
