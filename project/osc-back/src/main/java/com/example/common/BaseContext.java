package com.example.common;

/**
 * 基于ThreadLocal，用于保存和获取用户id的工具类
 * @author lwy
 */
public class BaseContext {

    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }

    public static void removeCurrentUser(){
        threadLocal.remove();
    }


}
