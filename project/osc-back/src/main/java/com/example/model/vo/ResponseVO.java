package com.example.model.vo;

import com.example.enums.ErrorCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 *
 * @param <T>
 * @author Uncommon
 */
@Data
public class ResponseVO<T> implements Serializable {
    // 返回码
    @Schema(description = "返回码")
    private int code;

    // 返回值信息
    @Schema(description = "返回简要信息")
    private String message;

    // 返回值数据
    @Schema(description = "返回数据")
    private T data;

    // 返回值描述
    @Schema(description = "返回详细描述")
    private String description;

    public ResponseVO(int code, T data, String message, String description) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.description = description;
    }

    public ResponseVO(int code, T data, String message) {
        this(code, data, message, "");
    }

    public ResponseVO(int code, T data) {
        this(code, data, "", "");
    }

    public ResponseVO(ErrorCodeEnum errorCodeEnum) {
        this(errorCodeEnum.getCode(), null, errorCodeEnum.getMessage(), errorCodeEnum.getDescription());
    }

}
