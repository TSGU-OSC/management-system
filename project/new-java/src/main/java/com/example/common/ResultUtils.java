package com.example.common;

/**
 *  统一返回工具类
 *
 * @author Uncommon
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param data
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0,data,"OK");
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败
     *
     * @param errorCode
     * @param message
     * @param description
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String message,String description) {
        return new BaseResponse(errorCode.getCode(),null,message,description);
    }

    /**
     * 失败
     *
     * @param errorCode
     * @param description
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode,String description) {
        return new BaseResponse(errorCode.getCode(),null,errorCode.getMessage(),description);
    }

    /**
     * 失败
     *
     * @param code
     * @param message
     * @param description
     * @return
     */
    public static BaseResponse error(int code, String message,String description) {
        return new BaseResponse(code,null,message,description);
    }
}
