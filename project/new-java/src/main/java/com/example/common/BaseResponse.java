package com.example.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 *
 * @param <T>
 * @author Uncommon
 */
@Data
public class BaseResponse<T> implements Serializable {
    // 返回码
    private int code;

    // 返回值信息
    private String message;

    // 返回值数据
    private T data;

    // 返回值描述
    private String description;

    public BaseResponse(int code, T data, String message,String description) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.description = description;
    }
    public BaseResponse(int code, T data,String message){
        this(code, data,message,"");
    }
    public BaseResponse(int code, T data){
        this(code, data,"","");
    }
    public BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(),null,errorCode.getMessage(),errorCode.getDescription());
    }

}
