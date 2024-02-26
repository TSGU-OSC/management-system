package com.example.model.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.example.config.ExcelConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * EasyExcel 写对象实体
 *
 * @author lwy
 */
@Schema(description = "Excel用户导出请求体")
@Data
public class ExcelOutput implements Serializable {

    @Serial
    private static final long serialVersionUID = 4477000910439586388L;

    /**
     * 用户id
     */
    @Schema(description = "用户ID")
    @ExcelProperty(value = "用户ID")
    private Long id;

    /**
     * 学号
     */
    @Schema(description = "学号")
    @ExcelProperty(value = "学号")
    private String code;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @ExcelProperty(value = "姓名")
    private String name;

    /**
     * 姓别
     */
    @Schema(description = "姓别（1-男 0-女）")
    @ExcelProperty(value = "姓别")
    private int gender;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @ExcelProperty(value = "手机号")
    private String phone;

    /**
     * 所在省
     */
    @Schema(description = "所在省")
    @ExcelProperty(value = "所在省")
    private String province;

    /**
     * 所在市
     */
    @Schema(description = "所在市")
    @ExcelProperty(value = "所在市")
    private String city;

    /**
     * 班级
     */
    @Schema(description = "班级")
    @ExcelProperty(value = "班级")
    private Integer clazz;

    /**
     * 专业
     */
    @Schema(description = "专业")
    @ExcelProperty(value = "专业")
    private String major;

    /**
     * 学院
     */
    @Schema(description = "学院")
    @ExcelProperty(value = "学院")
    private String academy;

    /**
     * 职位（0成员 1副部长 2 部长 3副社长 4社长）
     */
    @Schema(description = " 职位（0成员 1副部长 2 部长 3副社长 4社长）")
    @ExcelProperty(value = "职位")
    private Integer duty;

    /**
     * 部门（0-运营部，1-技术部，2-宣传部）
     */
    @Schema(description = " 部门（0-运营部，1-技术部，2-宣传部）")
    @ExcelProperty(value = "部门")
    private Integer department;

    /**
     * 个人介绍
     */
    @Schema(description = "个人介绍")
    @ExcelProperty(value = "个人介绍")
    private String introduction;

    /**
     * 身份证号码
     */
    @Schema(description = "身份证号")
    @ExcelProperty(value = "身份证号")
    private String idCard;

    /**
     * 状态（0正常 1封号）
     */
    @Schema(description = "状态（0正常 1封号）")
    @ExcelProperty(value = "状态")
    private Integer status;

    /**
     * 角色（0普通成员，1管理员，2超管）
     */
    @Schema(description = "角色（0普通成员，1管理员，2超管 默认为0）")
    @ExcelProperty(value = "角色")
    private Integer role;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @ExcelProperty(value = "创建时间", converter = ExcelConverter.class)
    private String createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @ExcelProperty(value = "更新时间", converter = ExcelConverter.class)
    private String updateTime;

    /**
     * 创建者
     */
    @Schema(description = "创建者")
    @ExcelProperty(value = "创建者ID")
    private Long createUser;

    /**
     * 更新者
     */
    @Schema(description = "更新者")
    @ExcelProperty(value = "更新者ID")
    private Long updateUser;
}
