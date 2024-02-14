package com.example.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author lwy
 */
@Schema(description = "用户登录请求参数")
@Data
public class UserLoginDTO implements Serializable {
    private static final long serialVersionUID = -7188479473325271448L;

    @Schema(description = "学号（非空）")
    @NotBlank(message = "学号不能为空")
    private String code;

    @Schema(description = "密码（非空，长度大于6）")
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, message = "密码需大于六位")
    private String password;

    /**
     * 验证码Key
     */
    @Schema(description = "验证码key")
    @NotBlank(message = "验证码key不能为空")
    private String verifyCodeKey;
    /**
     * 验证码
     */
    @Schema(description = "验证码结果")
    @NotBlank(message = "验证码结果不能为空")
    private String verifyCode;

}
