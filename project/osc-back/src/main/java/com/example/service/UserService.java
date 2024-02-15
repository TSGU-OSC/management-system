package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.dto.QueryDTO;
import com.example.model.dto.UserAddDTO;
import com.example.model.entity.User;

import java.util.List;
import java.util.Map;


/**
 * 用户服务
 *
 * @author lwy
 */
public interface UserService extends IService<User> {


    /**
     * 添加用户
     *
     * @return
     */
    long addUser(UserAddDTO userAddDTO);


    /**
     * 查询用户
     *
     * @param queryDTO 查询用户DTO类
     * @return 用户列表
     */
    List<User> listUsers(QueryDTO queryDTO);

    /**
     * 更改用户
     *
     * @param user 需更改的用户
     * @return 更改后的用户
     */
    User updateUser(User user);

    /**
     * 用户脱敏
     *
     * @param originUser 脱敏前的用户
     * @return 脱敏后用户信息
     */
    User getSafetyUser(User originUser);


    /**
     * 统计男/女人数
     *
     * @param gender 男-1/女-0
     * @return 男/女总人数
     */
    long countGender(int gender);

    /**
     * 统计各省人数
     *
     * @return 各省人数列表
     */
    List<Map<String, Object>> countProvince();
}
