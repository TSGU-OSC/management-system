package com.example.constant;

/**
 * 用户常量类
 *
 * @author osc
 */
public class UserConstant {
    /**
     * 用户登录状态键
     */
    public static final String USER_LOGIN_STATE = "userLoginState";

    /**
     * 对密码进行加盐加密（加盐就是让密码加密后更复杂）
     */
//    public static final String SALT = "osc";

    //超级管理员权限
    public static final int SUPER_ADMIN_USER = 2;
    //管理员权限
    public static final int ADMIN_USER = 1;
    //普通权限
    public static final int DEFAULT_USER = 0;

    //登录验证码过期时间 单位：秒
    public static final int CODE_EXPIRE_TIME = 60;

}
