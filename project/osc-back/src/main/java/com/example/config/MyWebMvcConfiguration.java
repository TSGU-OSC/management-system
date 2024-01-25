package com.example.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvc 配置类
 *
 * @author lwy
 */
@Configuration
@ComponentScan
public class MyWebMvcConfiguration implements WebMvcConfigurer {

    @Resource
    private MyInterceptor interceptor;

    /**
     * 配置拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有/user/请求，放行/user/login登录/user/verify获取验证码请求
        registry.addInterceptor(this.interceptor).addPathPatterns("/user/**").excludePathPatterns("/user/login", "/user/verify");

    }

}
