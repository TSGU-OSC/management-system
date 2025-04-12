package com.example.utils;

import cn.dev33.satoken.stp.StpInterface;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.BaseContext;
import com.example.mapper.UserMapper;
import com.example.model.entity.User;

import java.util.ArrayList;
import java.util.List;

public class StpInterfaceImpl extends ServiceImpl<UserMapper, User> implements StpInterface {
    @Override
    public List<String> getPermissionList(Object o, String s) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object userid,String type) {

        Long currentId = BaseContext.getCurrentId();
        User currentUser = this.getById(currentId);
        ArrayList<String> list = new ArrayList<>();
        list.add(currentUser.getRole());
        return list;

    }
}
