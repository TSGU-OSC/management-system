package com.example.common;

import com.example.enums.ErrorCodeEnum;
import com.example.model.vo.ResponseVO;

/**
 *  统一返回工具类
 *
 * @author lwy
 */
public class ResultUtils {

    /**
     * 成功
     */
    public static <T> ResponseVO<T> success(T data) {
        return new ResponseVO<>(200,data,"OK");
    }

    /**
     * 失败
     */
    public static <T> ResponseVO<T> error(ErrorCodeEnum errorCodeEnum) {
        return new ResponseVO<>(errorCodeEnum);
    }

    /**
     * 失败
     */
    public static ResponseVO<Object> error(ErrorCodeEnum errorCodeEnum, String message, String description) {
        return new ResponseVO<>(errorCodeEnum.getCode(),null,message,description);
    }

    /**
     * 失败
     */
    public static ResponseVO<Object> error(ErrorCodeEnum errorCodeEnum, String description) {
        return new ResponseVO<>(errorCodeEnum.getCode(),null, errorCodeEnum.getMessage(),description);
    }

    /**
     * 失败
     */
    public static ResponseVO<Object> error(int code, String message, String description) {
        return new ResponseVO<>(code, null, message, description);
    }
}
