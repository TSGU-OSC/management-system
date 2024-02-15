package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.BaseContext;
import com.example.enums.ErrorCodeEnum;
import com.example.exception.BusinessException;
import com.example.mapper.UserMapper;
import com.example.model.dto.QueryDTO;
import com.example.model.dto.UserAddDTO;
import com.example.model.entity.User;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.constant.UserConstant.*;

/**
 * 用户服务实现类
 *
 * @author lwy
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 添加用户
     *
     * @param userAddDTO 用户添加DTO类
     * @return 添加的用户id
     */
    @Override
    public long addUser(UserAddDTO userAddDTO) {
        // 学号不可重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", userAddDTO.getCode());
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCodeEnum.PARAMS_ERROR, "学号重复");
        }
        // 加密密码
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userAddDTO.getPassword()).getBytes());
        // 建立用户实体
        User user = new User();
        user.setCode(userAddDTO.getCode());
        user.setPassword(encryptPassword);
        user.setName(userAddDTO.getName());
        user.setGender(userAddDTO.getGender());
        user.setClazz(userAddDTO.getClazz());
        user.setMajor(userAddDTO.getMajor());
        user.setAcademy(userAddDTO.getAcademy());
        user.setDuty(userAddDTO.getDuty());
        user.setRole(userAddDTO.getRole());
        // 存储用户信息
        this.save(user);
        // 用户添加成功
        return user.getId();
    }

    /**
     * 查询用户
     *
     * @param queryDTO 查询用户DTO类
     * @return 用户列表
     */
    @Override
    public List<User> listUsers(QueryDTO queryDTO) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 非空查询
        lambdaQueryWrapper.like(org.apache.commons.lang3.StringUtils.isNotEmpty(queryDTO.getCode()), User::getCode, queryDTO.getCode())
                .like(org.apache.commons.lang3.StringUtils.isNotEmpty(queryDTO.getMajor()), User::getMajor, queryDTO.getMajor())
                .like(org.apache.commons.lang3.StringUtils.isNotEmpty(queryDTO.getName()), User::getName, queryDTO.getName())
                .like(org.apache.commons.lang3.StringUtils.isNotEmpty(queryDTO.getCity()), User::getCity, queryDTO.getCity())
                .like(org.apache.commons.lang3.StringUtils.isNotEmpty(queryDTO.getAcademy()), User::getAcademy, queryDTO.getAcademy())
                .like(org.apache.commons.lang3.StringUtils.isNotEmpty(queryDTO.getPhone()), User::getPhone, queryDTO.getPhone())
                .like(org.apache.commons.lang3.StringUtils.isNotEmpty(queryDTO.getProvince()), User::getProvince, queryDTO.getProvince())
                .like(queryDTO.getDuty() != null, User::getDuty, queryDTO.getDuty())
                .like(queryDTO.getClazz() != null, User::getClazz, queryDTO.getClazz())
                .like(queryDTO.getStatus() != null, User::getStatus, queryDTO.getStatus())
                .like(queryDTO.getRole() != null, User::getRole, queryDTO.getRole());

        List<User> list = this.list(lambdaQueryWrapper);
        // 如果用户权限低，对查询到的用户进行脱敏
        Long currentId = BaseContext.getCurrentId();
        if (this.getById(currentId).getRole() == DEFAULT_USER) {
            list = list.stream().map(this::getSafetyUser).toList();
        }
        return list;
    }

    /**
     * 更改用户
     *
     * @param user 需更改的用户
     * @return 更改后的用户
     */
    @Override
    public User updateUser(User user) {
        // 获取当前登录用户
        Long currentUserId = BaseContext.getCurrentId();
        User currentUser = this.getById(currentUserId);
        // 学号不能修改
        if (!Objects.equals(user.getCode(), this.getById(user.getId()).getCode())) {
            throw new BusinessException(ErrorCodeEnum.NO_AUTH, "学号不可修改");
        }
        // 如果是修改自己的信息
        if (currentUserId.equals(user.getId())) {
            // 超级管理员才能修改自己的 role status duty
            if (currentUser.getRole() != SUPER_ADMIN_USER &&
                    (!currentUser.getRole().equals(user.getRole()) ||
                            !currentUser.getStatus().equals(user.getStatus()) ||
                            !currentUser.getDuty().equals(user.getDuty()))) {
                throw new BusinessException(ErrorCodeEnum.NO_AUTH, "权限不足");
            }
        } else {  // 如果不是修改自己的信息
            // 超管才能修改他人的 role status duty ，除非修改者的权限高于被修改者，
            // 并且修改者不能将被修改者的权限改至高于等于修改者的权限
            if (currentUser.getRole() != SUPER_ADMIN_USER &&
                    currentUser.getRole() <=
                            (Math.max(user.getRole(), this.getById(user.getId()).getRole()))) {
                throw new BusinessException(ErrorCodeEnum.NO_AUTH, "权限不足");
            }
        }
        // 权限足够，可以修改
        this.updateById(user);
        // 获取更改后的用户
        return this.getById(user.getId());
    }

    /**
     * 用户脱敏
     *
     * @param originUser 脱敏前的用户
     * @return 脱敏后用户信息
     */
    @Override
    public User getSafetyUser(User originUser) {
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setCode(originUser.getCode());
        safetyUser.setAvator(originUser.getAvator());
        safetyUser.setName(originUser.getName());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setProvince(originUser.getProvince());
        safetyUser.setCity(originUser.getCity());
        safetyUser.setClazz(originUser.getClazz());
        safetyUser.setMajor(originUser.getMajor());
        safetyUser.setAcademy(originUser.getAcademy());
        safetyUser.setDuty(originUser.getDuty());
        safetyUser.setIntroduction(originUser.getIntroduction());

        return safetyUser;
    }

    /**
     * 统计男/女人数
     *
     * @param gender 男-1/女-0
     * @return 男/女总人数
     */
    @Override
    public long countGender(int gender) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getGender, gender);
        return this.count(queryWrapper);
    }

    /**
     * 统计各省人数
     *
     * @return 各省人数列表
     */
    @Override
    public List<Map<String, Object>> countProvince() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("count(*) as count,province").groupBy("province");
        return this.listMaps(queryWrapper);
    }
}