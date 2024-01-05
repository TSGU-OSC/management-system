package cn.osc.result;

import com.baomidou.mybatisplus.extension.api.R;

import java.io.Serializable;

/**
 * @Author: fsq
 * @Date: 2024/1/3
 * @Description: 统一结果封装
 **/

public class Result<T> implements Serializable  {
    //响应码
    private Integer code;
    //信息
    private String message;
    //返回数据
    private Object data;

    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public static Result error(String msg)
    {
        return new Result(400, msg,"");
    }

    public static Result success(String msg, Object data)
    {
        return new Result(200, msg, data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
