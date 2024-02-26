package com.example.model.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

/**
 * EasyExcel 读对象实体
 *
 * @author lwy
 */
@Schema(description = "Excel用户导入请求体")
@Data
public class ExcelInput implements Serializable {

    @Serial
    private static final long serialVersionUID = -5760139106419386208L;

    // 姓名
    @ExcelProperty(value = "姓名")
    @Schema(description = "姓名（非空）")
    @NotBlank(message = "姓名不能为空")
    private String name;

    // 姓别
    @ExcelProperty(value = "姓别")
    @Schema(description = "姓别（1-男 0-女）")
    @NotNull(message = "姓别不能为空")
    private int gender;

    // 手机号
    @ExcelProperty(value = "手机号")
    @Schema(description = "手机号")
    @NotBlank(message = "手机号不能为空字符")
    private String phone;

    // 所在省
    @ExcelProperty(value = "所在省")
    @Schema(description = "所在省")
    private String province;

    // 所在市
    @ExcelProperty(value = "所在市")
    @Schema(description = "所在市")
    private String city;

    // 学号
    @ExcelProperty(value = "学号")
    @Schema(description = "学号（长度为11）")
    @Length(min = 11, max = 11, message = "学号不规范")
    private String code;

    // 班级
    @ExcelProperty(value = "班级")
    @Schema(description = "班级（非空）")
    @NotNull(message = "班级不能为空")
    private Integer clazz;

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

    // 职位（0成员 1副部长 2 部长 3副社长 4社长） 默认为0
    @ExcelProperty(value = "职位")
    @Schema(description = "职位（（0成员 1副部长 2 部长 3副社长 4社长） 默认为0）")
    private Integer duty;

    // 部门（0-运营部，1-技术部，2-宣传部）
    @ExcelProperty(value = "部门")
    @Schema(description = " 部门（0-运营部，1-技术部，2-宣传部）")
    private Integer department;

    // 个人介绍
    @ExcelProperty(value = "个人介绍")
    @Schema(description = "个人介绍")
    private String introduction;
}
