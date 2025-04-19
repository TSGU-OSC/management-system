package com.example.utils;

import cn.dev33.satoken.stp.StpInterface;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.BaseContext;
import com.example.mapper.UserMapper;
import com.example.model.entity.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StpInterfaceImpl extends ServiceImpl<UserMapper, User> implements StpInterface {
    @Override
    public List<String> getPermissionList(Object o, String s) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object userid,String type) {

//        Long currentId = BaseContext.getCurrentId();
        User currentUser = this.getById((Serializable) userid);
        ArrayList<String> list = new ArrayList<>();
        Integer role = currentUser.getRole();
        switch (role){
            case 0:
                list.add("ROLE_MEMBER");
                break;
            case 1:
                list.add("ROLE_ADMIN");
                break;
            case 2:
                list.add("ROLE_SUPER_ADMIN");
                break;
        }
        return list;

    }
}
