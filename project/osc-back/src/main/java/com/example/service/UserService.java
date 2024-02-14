package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.dto.QueryDTO;
import com.example.model.dto.UserAddDTO;
import com.example.model.dto.UserLoginDTO;
import com.example.model.dto.VerifyCodeDTO;
import com.example.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;


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
    long addUser(UserAddDTO userAddDTO);

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
     * 查询用户
     *
     * @param queryDTO 查询用户DTO类
     * @return 用户列表
     */
    List<User> listUsers(QueryDTO queryDTO);

    /**
     * 更改用户
     *
     * @param user 需更改的用户
     * @return 更改后的用户
     */
    User updateUser(User user);

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
