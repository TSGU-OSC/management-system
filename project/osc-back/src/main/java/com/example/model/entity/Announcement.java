package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 公告实体类
 *
 * @author lwy
 */
@TableName(value = "announcement")
@Data
public class Announcement implements Serializable {
    /**
     * 公告id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "公告id")
    @NotNull
    private Long id;

    /**
     * 公告标题
     */
    @TableField(value = "title")
    @Schema(description = "公告标题")
    @NotBlank
    private String title;

    /**
     * 公告内容
     */
    @TableField(value = "content")
    @Schema(description = "公告内容")
    private String content;

    /**
     * 公告状态（0普通公告 1紧急公告）
     */
    @TableField(value = "status")
    @Schema(description = "公告状态（0普通公告 1紧急公告）")
    private Integer status;

    /**
     * 创建人
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    @Schema(description = "创建人id")
    private Long createUser;

    /**
     * 修改人
     */
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "修改人id")
    private Long updateUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "修改时间")
    private Date updateTime;

    /**
     * 是否删除(0未删除 1已删除)
     */
    @TableField(value = "is_deleted")
    @TableLogic
    @Schema(description = "是否删除(0未删除 1已删除)")
    private Integer isDeleted;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 3252207553356908448L;
}