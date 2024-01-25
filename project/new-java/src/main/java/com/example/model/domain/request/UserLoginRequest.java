package com.example.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author lwy
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID= -7188479473325271448L;

    private String code;

    private String password;

}
