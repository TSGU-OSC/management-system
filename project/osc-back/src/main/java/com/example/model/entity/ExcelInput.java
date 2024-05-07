package com.example.model.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Schema(description = "Excel导入数据")
@Data
public class ExcelInput implements Serializable {

    @Serial
    private static final long serialVersionUID = -5760139106419386208L;

    // 姓名
    @ExcelProperty(value = "姓名")
    @Schema(description = "姓名（非空）")
    @NotBlank(message = "姓名不能为空")
    private String name;

    // 性别
    @ExcelProperty(value = "性别")
    @Schema(description = "性别（1-男 0-女）")
    @NotBlank(message = "性别不能为空")
    private String gender;

    // 手机号
    @ExcelProperty(value = "手机号")
    @Schema(description = "手机号")
    @NotBlank(message = "手机号不能为空字符")
    private String phone;

    // 所在地区（省-市）
    @ExcelProperty(value = "地区")
    @Schema(description = "地区")
    private String place;

    // 学号
    @ExcelProperty(value = "学号")
    @Schema(description = "学号（长度为11）")
    private String code;

    // 班级
    @ExcelProperty(value = "班级")
    @Schema(description = "班级（非空）")
    @NotBlank(message = "班级不能为空")
    private String clazz;

    // 专业
    @ExcelProperty(value = "专业")
    @Schema(description = "专业（非空）")
    @NotBlank(message = "专业不能为空")
    private String major;

    // 学院
    @ExcelProperty(value = "学院")
    @Schema(description = "学院（非空）")
    @NotBlank(message = "学院不能为空")
    private String academy;

    // 部门（0-运营部，1-技术部，2-宣传部）
    @ExcelProperty(value = "部门")
    @Schema(description = " 部门（0-运营部，1-技术部，2-宣传部）")
    private String department;

    // 个人介绍
    @ExcelProperty(value = "个人介绍")
    @Schema(description = "个人介绍")
    private String introduction;
}