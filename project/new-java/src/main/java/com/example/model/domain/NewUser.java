package com.example.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName new_user
 */
@TableName(value ="new_user")
@Data
public class NewUser implements Serializable {
    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学号
     */
    @TableField(value = "code")
    private String code;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 头像
     */
    @TableField(value = "avator")
    private String avator;

    /**
     * 姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 所在省
     */
    @TableField(value = "province")
    private String province;

    /**
     * 所在市
     */
    @TableField(value = "city")
    private String city;

    /**
     * 班级
     */
    @TableField(value = "clazz")
    private Integer clazz;

    /**
     * 专业
     */
    @TableField(value = "major")
    private String major;

    /**
     * 学院
     */
    @TableField(value = "academy")
    private String academy;

    /**
     * 职位（0成员 1副部长 2 部长 3副社长 4社长）
     */
    @TableField(value = "duty")
    private Integer duty;

    /**
     * 个人介绍
     */
    @TableField(value = "introduction")
    private String introduction;

    /**
     * 身份证号码
     */
    @TableField(value = "ID_card")
    private String idCard;

    /**
     * 状态（0正常 1封号）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 是否删除（0正常 1删除）
     */
    @TableLogic
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 创建者
     */
    @TableField(value = "create_user")
    private Integer createUser;

    /**
     * 更新者
     */
    @TableField(value = "update_user")
    private Integer updateUser;

    /**
     * 角色
     */
    @TableField(value = "rule")
    private Integer rule;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}