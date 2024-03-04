package com.example.exception;

import com.example.common.ResultUtils;
import com.example.enums.ErrorCodeEnum;
import com.example.model.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author Uncommon
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseVO<Object> businessExceptionHandler(BusinessException e) {
        log.error("businessException: " + e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseVO<Object> runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException: ", e);
        return ResultUtils.error(ErrorCodeEnum.SYSTEM_ERROR, e.getMessage(), "");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseVO<Object> argumentExceptionHandler(MethodArgumentNotValidException e) {
        String result = "";
        //查看MethodArgumentNotValidException类可以发现,异常的信息在BindingResult下List<ObjectError>中
        //我这里取第一条的信息进行展示,可根据实际项目情况自行修改
        //getDefaultMessage()获取的信息就是我们RequestVerificationCode中的message部分
        if (!e.getBindingResult().getAllErrors().isEmpty()) {
            result = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        }
        log.error("MethodArgumentNotValidException:", e);
        return ResultUtils.error(ErrorCodeEnum.PARAMS_ERROR, "请求参数错误", result);
    }
}
