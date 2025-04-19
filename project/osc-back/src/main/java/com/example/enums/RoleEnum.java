package com.example.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum RoleEnum {
    ROLE_MEMBER(0,"ROLE_MEMBER"),
    ROLE_ADMIN(1,"ROLE_ADMIN"),
    ROLE_SUPER_ADMIN(2,"ROLE_SUPER_ADMIN");

    private final Integer num;
    @Getter
    private final String msg;

    private static final Map<Integer, RoleEnum> NAME_MAP = new HashMap<>();

    static {
        for (RoleEnum role : RoleEnum.values()) {

            NAME_MAP.put(role.num, role);
        }
    }

    RoleEnum(Integer i,String s){
        this.msg = s;
        this.num = i;

    }
    public Integer getRoleNum(){
        return num;
    }

    public static String getMsgByNum(Integer num){

        RoleEnum roleEnum = NAME_MAP.get(num);
        return roleEnum.msg;

    }

}
