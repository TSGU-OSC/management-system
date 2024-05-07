package com.example.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 公告添加请求体
 *
 * @author osc
 */
@Schema(description = "添加公告请求体")
@Data
public class AnnouncementAddDTO implements Serializable {
    private static final long serialVersionUID = -5001088178101658884L;

    /**
     * 公告标题
     */
    @Schema(description = "公告标题")
    @NotBlank
    private String title;

    /**
     * 公告内容
     */
    @Schema(description = "公告内容")
    private String content;


    /**
     * 公告级别（0普通公告 1紧急公告）
     */
    @Schema(description = "公告级别")
    private Integer status;
}
