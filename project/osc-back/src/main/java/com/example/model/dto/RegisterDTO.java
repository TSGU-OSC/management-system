package com.example.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @author fsq
 */
@Schema(description = "用户注册请求体")
@Data
public class RegisterDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 504966900305465423L;

    // 姓名
    @Schema(description = "姓名（非空）")
    @NotBlank(message = "姓名不能为空")
    private String name;

    // 性别
    @Schema(description = "性别（1-男 0-女）")
    @NotNull(message = "性别不能为空")
    private int gender;

    // 学号
    @Schema(description = "学号（长度为11）")
    @Length(min = 11, max = 11, message = "学号不符合规范")
    private String code;

    // 密码
    @Schema(description = "密码（默认12345678）")
    @NotBlank(message = "密码不能为空")
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

    /**
     * 所在省
     */
    @Schema(description = "所在省")
    @TableField(value = "province")
    private String province;

    /**
     * 所在市
     */
    @Schema(description = "所在市")
    @TableField(value = "city")
    private String city;

    /**
     * 个人介绍
     */
    @Schema(description = "个人介绍")
    @TableField(value = "introduction")
    private String introduction;

    /**
     * 身份证号码
     */
    @Schema(description = "身份证号")
    @TableField(value = "id_card")
    private String idCard;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @TableField(value = "phone")
    private String phone;



    // 部门（0-运营部，1-技术部，2-宣传部，3-其它-默认）
    @Schema(description = " 部门（0-运营部，1-技术部，2-宣传部，3-其它-默认）")
    private Integer department;

}
