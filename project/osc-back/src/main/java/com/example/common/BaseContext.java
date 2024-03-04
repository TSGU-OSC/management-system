package com.example.common;

/**
 * 基于ThreadLocal，用于保存和获取用户id的工具类
 *
 * @author lwy
 */
public class BaseContext {

    private static final ThreadLocal<Long> THREAD_LOCAL = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        THREAD_LOCAL.set(id);
    }

    public static Long getCurrentId() {
        return THREAD_LOCAL.get();
    }

    public static void removeCurrentUser() {
        THREAD_LOCAL.remove();
    }


}
