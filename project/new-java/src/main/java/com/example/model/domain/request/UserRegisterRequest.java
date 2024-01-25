package com.example.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author lwy
 */
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID= 504966900305465423L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String planetCode;
}
