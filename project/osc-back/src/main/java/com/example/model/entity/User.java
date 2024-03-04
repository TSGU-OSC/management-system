package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户实体
 *
 * @author lwy
 */
@TableName(value = "user")
@Data
public class User implements Serializable {
    /**
     * 用户id
     */
    @Schema(description = "用户ID")
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull
    private Long id;

    /**
     * 学号
     */
    @Schema(description = "学号")
    @TableField(value = "code")
    @Length(min = 11, max = 11, message = "学号不规范")
    private String code;

    /**
     * 密码
     */
    @Schema(description = "密码")
    @TableField(value = "password")
    @NotBlank(message = "密码不能为空字符")
    private String password;

    /**
     * 头像
     */
    @Schema(description = "头像")
    @TableField(value = "avator")
    private String avator;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @TableField(value = "name")
    @NotBlank(message = "姓名不能为空")
    private String name;

    /**
     * 姓别
     */
    @Schema(description = "姓别（1-男 0-女）")
    @TableField(value = "gender")
    @NotBlank(message = "姓别不能为空")
    private int gender;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @TableField(value = "phone")
    private String phone;

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
     * 班级
     */
    @Schema(description = "班级")
    @TableField(value = "clazz")
    @NotNull(message = "班级不能为空")
    private Integer clazz;

    /**
     * 专业
     */
    @Schema(description = "专业")
    @TableField(value = "major")
    @NotNull(message = "专业不能为空")
    private String major;

    /**
     * 学院
     */
    @Schema(description = "学院")
    @TableField(value = "academy")
    @NotNull(message = "学院不能为空")
    private String academy;

    /**
     * 职位（0成员 1副部长 2 部长 3副社长 4社长）
     */
    @Schema(description = " 职位（0成员 1副部长 2 部长 3副社长 4社长）")
    @TableField(value = "duty")
    @NotBlank(message = "职责不能为空")
    private Integer duty;

    /**
     * 部门（0-运营部，1-技术部，2-宣传部，3-其它-默认）
     */
    @Schema(description = " 部门（0-运营部，1-技术部，2-宣传部，3-其它-默认）")
    @TableField(value = "department")
    private Integer department;

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
    @TableField(value = "ID_card")
    private String idCard;

    /**
     * 状态（0正常 1封号）
     */
    @Schema(description = "状态（0正常 1封号）")
    @TableField(value = "status")
    private Integer status;

    /**
     * 角色（0普通成员，1管理员，2超管）
     */
    @Schema(description = "角色（0普通成员，1管理员，2超管 默认为0）")
    @TableField(value = "role")
    private Integer role;

    /**
     * 是否删除（0正常 1删除）
     */
    @Schema(description = "是否删除（0正常 1删除）")
    @TableLogic
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建者
     */
    @Schema(description = "创建者")
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 更新者
     */
    @Schema(description = "更新者")
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = -7373203003403525090L;
}