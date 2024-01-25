package com.example.config;

import com.example.common.BaseContext;
import com.example.common.ErrorCode;
import com.example.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 请求拦截器
 *
 * @author lwy
 */
@Component
@Slf4j
public class MyInterceptor implements HandlerInterceptor {

    /**
     * 请求执行前执行的，将用户id放入ThreadLocal
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Object id = request.getSession().getAttribute("USER_LOGIN_STATE");
        if (id == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "未登录");
        }
        log.info("设置ThreadLocal用户信息");
        BaseContext.setCurrentId((Long) id);
        return true;
    }

    /**
     * 接口访问结束后，从ThreadLocal中删除用户信息
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("接口调用结束， 从ThreadLocal删除用户信息");
        BaseContext.removeCurrentUser();
    }
}
