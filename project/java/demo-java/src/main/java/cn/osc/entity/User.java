package cn.osc.entity;

import com.baomidou.mybatisplus.annotation.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: fsq
 * @Date: 2024/1/3
 * @Description: 用户实体类
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "姓名")
    @NotEmpty(message = "姓名不能为空！")
    private String username;

    @ApiModelProperty(value = "性别")
    @NotEmpty
    private String gender;

    @ApiModelProperty(value = "密码")
    @NotEmpty
    private String password;

    @ApiModelProperty(value = "学号")
    @NotEmpty
    private String code;

    @NotEmpty
    @ApiModelProperty(value = "班级")
    private String clazz;

    @NotEmpty
    @ApiModelProperty(value = "专业")
    private String major;

    @NotEmpty
    @ApiModelProperty(value = "学院")
    private String academy;


    @NotEmpty
    @ApiModelProperty(value = "职位")
    private String duty;


    @ApiModelProperty(value = "状态。0禁用 1启用")
    private  String  status = "1";

    @ApiModelProperty(value = "是否为管理员  0不是 1是")
    private Integer isAdmin;

    @ApiModelProperty(value = "个人介绍")
    private String introduction;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    //创建人
    @TableField(fill = FieldFill.INSERT)
    private Integer createUser;

    //修改人
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateUser;


}
