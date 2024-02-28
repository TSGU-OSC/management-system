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
    @Schema(description = "密码（默认12345678）")
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

    // 部门（0-运营部，1-技术部，2-宣传部，3-其它-默认）
    @Schema(description = " 部门（0-运营部，1-技术部，2-宣传部，3-其它-默认）")
    private Integer department;

    // 角色（0普通成员，1管理员，2超管）
    @Schema(description = "角色（0普通成员，1管理员，2超管 默认为0）")
    private Integer role;
}
