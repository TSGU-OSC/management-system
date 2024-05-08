package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.BaseContext;
import com.example.config.MinIOProperty;
import com.example.enums.ErrorCodeEnum;
import com.example.exception.BusinessException;
import com.example.mapper.UserMapper;
import com.example.model.dto.QueryDTO;
import com.example.model.dto.UserAddDTO;
import com.example.model.entity.User;
import com.example.service.FileService;
import com.example.service.UserService;
import com.example.utils.MinioUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.constant.UserConstant.*;

/**
 * 用户服务实现类
 *
 * @author osc
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Resource
    MinioUtil minioUtil;
    @Resource
    MinIOProperty minioProperty;

    /**
     * 添加用户
     *
     * @param userAddDTO 用户添加DTO类
     * @return 添加的用户id
     */
    @Override
    public long addUser(@NotNull UserAddDTO userAddDTO) {
        // 获取当前线程用户
        Long currentId = BaseContext.getCurrentId();
        User currentUser = this.getById(currentId);
        // 权限需要高于被创建用户的权限
        if (currentUser.getRole() <= userAddDTO.getRole()) {
            throw new BusinessException(ErrorCodeEnum.NO_AUTH, "权限不足");
        }
        // 学号不可重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", userAddDTO.getCode());
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCodeEnum.PARAMS_ERROR, "学号重复");
        }
        // 设置默认密码
        if (StringUtils.isBlank(userAddDTO.getPassword())) {
            userAddDTO.setPassword("12345678");
        }
        // 加密密码
//        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userAddDTO.getPassword()).getBytes());
        // 建立用户实体
        User user = new User();
        user.setCode(userAddDTO.getCode());
        user.setPassword(userAddDTO.getPassword());
        user.setName(userAddDTO.getName());
        user.setGender(userAddDTO.getGender());
        user.setClazz(userAddDTO.getClazz());
        user.setMajor(userAddDTO.getMajor());
        user.setAcademy(userAddDTO.getAcademy());
        user.setDuty(userAddDTO.getDuty());
        user.setDepartment(userAddDTO.getDepartment());
        user.setRole(userAddDTO.getRole());
        // 存储用户信息
        this.save(user);
        // 用户添加成功
        return user.getId();
    }

    /**
     * 查询用户
     *
     * @param queryDTO   查询用户DTO类
     * @param pageNumber 页码
     * @param pageSize   每页数目
     * @return 用户列表
     */
    public PageInfo<User> listUsers(QueryDTO queryDTO, Integer pageNumber, Integer pageSize) {
// 开始分页查询

        Page<Object> page = PageHelper.startPage(pageNumber, pageSize,"duty desc,code asc");
// 非空查询
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(queryDTO.getCode()), User::getCode, queryDTO.getCode())
                .like(StringUtils.isNotEmpty(queryDTO.getMajor()), User::getMajor, queryDTO.getMajor())
                .like(StringUtils.isNotEmpty(queryDTO.getName()), User::getName, queryDTO.getName())
                .like(StringUtils.isNotEmpty(queryDTO.getCity()), User::getCity, queryDTO.getCity())
                .like(StringUtils.isNotEmpty(queryDTO.getAcademy()), User::getAcademy, queryDTO.getAcademy())
                .like(StringUtils.isNotEmpty(queryDTO.getPhone()), User::getPhone, queryDTO.getPhone())
                .like(StringUtils.isNotEmpty(queryDTO.getProvince()), User::getProvince, queryDTO.getProvince())
                .like(queryDTO.getGender() != null, User::getGender, queryDTO.getGender())
                .like(queryDTO.getDuty() != null, User::getDuty, queryDTO.getDuty())
                .like(queryDTO.getDepartment() != null, User::getDepartment, queryDTO.getDepartment())
                .like(queryDTO.getClazz() != null, User::getClazz, queryDTO.getClazz())
                .like(queryDTO.getStatus() != null, User::getStatus, queryDTO.getStatus())
                .like(queryDTO.getRole() != null, User::getRole, queryDTO.getRole())
                .ne(User::getStatus, 2);

        List<User> list = this.list(lambdaQueryWrapper);
// 如果用户权限低，对查询到的用户进行脱敏
        List<User> safetyList = new ArrayList<>();
        list.forEach(item -> safetyList.add(getSafetyUser(item)));
//用PageInfo对结果进行包装
        PageInfo<User> userPageInfo = new PageInfo<>(safetyList);
// 取回原结果集的分页信息
        userPageInfo.setPages(page.getPages());
        userPageInfo.setTotal(page.getTotal());
        return userPageInfo;
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
        // 平级用户不能修改平级用户信息
        if (currentUser.getRole() <= user.getRole() && currentUser.getRole()!=SUPER_ADMIN_USER && !Objects.equals(currentUser.getId(), user.getId())) {
            throw new BusinessException(ErrorCodeEnum.NO_AUTH, "权限不足");
        }
        // 学号不能为空
        if(user.getCode() == null||user.getCode().length()!=11){
            throw new BusinessException(ErrorCodeEnum.PARAMS_ERROR, "学号不符合规范");
        }
        // 学号不可重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", user.getCode());
        queryWrapper.ne("id", user.getId());
        long count = this.count(queryWrapper);
        if (count>0) {
            throw new BusinessException(ErrorCodeEnum.PARAMS_ERROR, "学号重复");
        }
        // 密码非空
        if (StringUtils.isBlank(user.getPassword())) {
            throw new BusinessException(ErrorCodeEnum.PARAMS_ERROR, "密码不能为空");
        }
        // 如果是修改自己的信息
        if (currentUserId.equals(user.getId())) {
            // 超级管理员才能修改自己的 role status duty department
            if (currentUser.getRole() != SUPER_ADMIN_USER &&
                    (!currentUser.getRole().equals(user.getRole()) ||
                            !currentUser.getStatus().equals(user.getStatus()) ||
                            !currentUser.getDuty().equals(user.getDuty()) ||
                            !currentUser.getDepartment().equals(user.getDepartment()))) {
                throw new BusinessException(ErrorCodeEnum.NO_AUTH, "只有超级管理员才可以修改此信息");
            }
        } else {  // 如果不是修改自己的信息
            // 超管才能修改他人的 role status duty department ，除非修改者的权限高于被修改者，
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
        User result = this.getById(user.getId());
        return result;
    }

    /**
     * 删除用户
     *
     * @param id 待删除用户id
     * @return 已删除用户的id
     */
    @Override
    public Long deleteUser(Long id) {
        User user = this.getById(id);
        // 获取当前登录用户
        Long currentId = BaseContext.getCurrentId();
        User currentUser = this.getById(currentId);
        // 只能删除权限低于自己的用户
        if (currentUser.getRole() <= user.getRole()) {
            throw new BusinessException(ErrorCodeEnum.NO_AUTH, "用户权限不足");
        }
        // 删除头像
        if(!"".equals(user.getAvator())){
            minioUtil.deleteFile(minioProperty.getBucket(), user.getAvator());
        }
        // 删除用户
        this.removeById(id);
        return id;
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
        // 获取当前登录用户
        Long currentId = BaseContext.getCurrentId();
        User currentUser = this.getById(currentId);

        if( currentUser.getRole() <=originUser.getRole()&&currentUser.getRole() != SUPER_ADMIN_USER && currentUser.getRole() != ADMIN_USER){
            safetyUser.setAvator(originUser.getAvator());
            safetyUser.setName(originUser.getName());
            safetyUser.setGender(originUser.getGender());
            safetyUser.setClazz(originUser.getClazz());
            safetyUser.setMajor(originUser.getMajor());
            safetyUser.setAcademy(originUser.getAcademy());
            safetyUser.setDuty(originUser.getDuty());
            safetyUser.setDepartment(originUser.getDepartment());
            safetyUser.setRole(originUser.getRole());
            safetyUser.setIntroduction(originUser.getIntroduction());
            safetyUser.setProvince(originUser.getProvince());
            safetyUser.setCity(originUser.getCity());
            return safetyUser;
        }
        return originUser;
    }

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户
     */
    @Override
    public User getCurrentUser() {
        // 获取当前登录用户
        Long currentId = BaseContext.getCurrentId();
        User currentUser = this.getById(currentId);
        return currentUser;
    }

    /**
     * 统计男/女人数
     *
     * @return 男/女总人数

    /**
     * 统计男女人数
     */
    @Override
    public long[] countGender() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getGender, 1);
        queryWrapper.ne(User::getStatus, 2);
        long[] result = new long[2];
        result[0] = this.count(queryWrapper);
        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getGender, 0);
        queryWrapper.ne(User::getStatus, 2);
        result[1] = this.count(queryWrapper);
        return result;
    }

    /**
     * 统计各省人数
     *
     * @return 各省人数列表
     */
    @Override
    public List<Map<String, Object>> countProvince() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("status",2);
        queryWrapper.select("count(*) as count,province").groupBy("province");
        return this.listMaps(queryWrapper);
    }

    @Override
    public PageInfo<User> countAudit(QueryDTO queryDTO, Integer pageNumber, Integer pageSize) {
        // 如果用户权限低 报错
        Long currentId = BaseContext.getCurrentId();
        if (this.getById(currentId).getRole() == DEFAULT_USER) {
            throw new BusinessException(ErrorCodeEnum.NO_AUTH, "普通用户无法查询待通过用户");
        }
        // 开始分页查询
        PageHelper.startPage(pageNumber, pageSize);
        // 非空查询
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(queryDTO.getCode()), User::getCode, queryDTO.getCode())
                .like(StringUtils.isNotEmpty(queryDTO.getName()), User::getName, queryDTO.getName())
                .ne(User::getStatus,0)
                .ne(User::getStatus,1)
                .like(queryDTO.getStatus() != null, User::getStatus, queryDTO.getStatus())
                .orderByDesc(User::getCreateTime);

        List<User> list = this.list(lambdaQueryWrapper);

        return new PageInfo<>(list);
    }
}