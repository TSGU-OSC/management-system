package com.example.exception;

import com.example.common.BaseResponse;
import com.example.common.ErrorCode;
import com.example.common.ResultUtils;
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
    public BaseResponse businessExceptionHandler(BusinessException e){
        log.error("businessException: "+e.getMessage(),e);
        return ResultUtils.error(e.getCode(),e.getMessage(),e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e){
        log.error("runtimeException: ",e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,e.getMessage(),"");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseResponse argumentExceptionHandler(MethodArgumentNotValidException e) {
        String resut = "";
        //查看MethodArgumentNotValidException类可以发现,异常的信息在BindingResult下List<ObjectError>中
        //我这里取第一条的信息进行展示,可根据实际项目情况自行修改
        //getDefaultMessage()获取的信息就是我们RequestVerificationCode中的message部分
        if (e.getBindingResult() != null
                && e.getBindingResult().getAllErrors() != null
                && e.getBindingResult().getAllErrors().size() > 0) {
            resut = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        }
        log.error("MethodArgumentNotValidException:",e);
        return ResultUtils.error(ErrorCode.PARAMS_ERROR,resut,"请求参数错误");
    }
}
