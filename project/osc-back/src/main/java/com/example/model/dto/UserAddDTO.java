package com.example.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author lwy
 */
@Schema(description = "添加用户请求体")
@Data
public class UserAddDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 504966900305465423L;

    // 姓名
    @Schema(description = "姓名（非空）")
    @NotBlank(message = "姓名不能为空")
    private String name;

    // 姓别
    @Schema(description = "姓别（1-男 0-女）")
    @NotNull(message = "姓别不能为空")
    private int gender;

    // 学号
    @Schema(description = "学号（长度为11）")
    @Length(min = 11, max = 11, message = "学号不规范")
    private String code;

    // 密码
    @Schema(description = "密码（不能为空或空字符，长度大于6）")
    @NotBlank(message = "密码不能为空字符")
    @Length(min = 6, message = "密码需大于六位")
    private String password;

    // 班级
    @Schema(description = "班级（非空）")
    @NotNull(message = "班级不能为空")
    private Integer clazz;

    // 专业
    @Schema(description = "专业（非空）")
    @NotBlank(message = "专业不能为空")
    private String major;

    // 学院
    @Schema(description = "学院（非空）")
    @NotBlank(message = "学院不能为空")
    private String academy;

    // 职位（0成员 1副部长 2 部长 3副社长 4社长） 默认为0
    @Schema(description = "职位（（0成员 1副部长 2 部长 3副社长 4社长） 默认为0）")
    private Integer duty;

    // 角色（0普通成员，1管理员，2超管）
    @Schema(description = "角色（0普通成员，1管理员，2超管）")
    @NotNull(message = "角色不能为空")
    private Integer role;
}
