package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.domain.NewUser;
import jakarta.servlet.http.HttpServletRequest;


/**
 * 用户服务
 *
 * @author lwy
 */
public interface NewUserService extends IService<NewUser> {

    /*   *//**
     * 用户注册
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @param planetCode 星球编号
     * @return 新用户ID
     *//*
    long userRegister(String userAccount,String userPassword,String checkPassword,String planetCode);
*/

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    NewUser userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户登出
     *
     * @param request
     * @return 是否成功
     */
    int userLogout(HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param originUser 脱敏前的用户
     * @return 脱敏后用户信息
     */
    NewUser getSafetyUser(NewUser originUser);

}
