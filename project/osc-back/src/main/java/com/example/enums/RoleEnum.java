package com.example.enums;

import lombok.Getter;

public enum RoleEnum {
    ROLE_MEMBER(0,"普通成员"),
    ROLE_ADMIN(1,"管理员"),
    ROLE_SUPER_ADMIN(2,"超管");

    private final Integer num;
    @Getter
    private final String msg;

    RoleEnum(Integer i,String s){
        this.msg = s;
        this.num = i;

    }
    public Integer getRoleNum(){
        return num;
    }

}
