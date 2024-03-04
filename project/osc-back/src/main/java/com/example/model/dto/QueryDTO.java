package com.example.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 查询用户DTO类
 *
 * @author lwy
 */
@Schema(description = "查询用户请求体")
@Data
public class QueryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 5231231861777401881L;
    /**
     * 学号
     */
    @Schema(description = "学号")
    private String code;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    private String name;

    /**
     * 姓别
     */
    @Schema(description = "姓别")
    private Integer gender;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 所在省
     */
    @Schema(description = "所在省")
    private String province;

    /**
     * 所在市
     */
    @Schema(description = "所在市")
    private String city;

    /**
     * 班级
     */
    @Schema(description = "班级")
    private Integer clazz;

    /**
     * 专业
     */
    @Schema(description = "专业")
    private String major;

    /**
     * 学院
     */
    @Schema(description = "学院")
    private String academy;

    /**
     * 职位（0成员 1副部长 2 部长 3副社长 4社长）
     */
    @Schema(description = "职位 （0成员 1副部长 2 部长 3副社长 4社长）")
    private Integer duty;

    /**
     * 部门（0-运营部，1-技术部，2-宣传部）
     */
    @Schema(description = " 部门（0-运营部，1-技术部，2-宣传部）")
    private Integer department;

    /**
     * 状态（0正常 1封号）
     */
    @Schema(description = "状态 （0正常 1封号）")
    private Integer status;

    /**
     * 角色（0普通成员，1管理员，2超管）
     */
    @Schema(description = "角色（0普通成员，1管理员，2超管）")
    private Integer role;

}
