package com.example.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 验证码实体
 *
 * @author lwy
 */
@Schema(description = "验证码实体")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCodeDTO implements Serializable {
    /**
     * 验证码Key
     */
    @NotBlank(message = "验证码key为空")
    @Schema(description = "验证码key")
    private String key;

    /**
     * 验证码图片，base64压缩后的字符串
     */
    @NotBlank(message = "验证码图片为空")
    @Schema(description = "验证码图片")
    private String image;

    /**
     * 验证码文本值
     */
    @Schema(description = "验证码文本值")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // 使text属性不会被序列化后返回给前端。
    private String text;
}