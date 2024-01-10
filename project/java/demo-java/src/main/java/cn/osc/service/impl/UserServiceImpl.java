package cn.osc.service.impl;

import cn.osc.config.BaseContext;
import cn.osc.dto.CountUserDTO;
import cn.osc.dto.QueryDTO;
import cn.osc.dto.UserDTO;
import cn.osc.entity.User;
import cn.osc.mapper.UserMapper;
import cn.osc.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author fsq
 * @Date 2024/1/6
 * @Description
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public IPage<User> selectUserPage(QueryDTO queryDTO) {
        Page<User> page=new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        UserDTO userDTO = queryDTO.getUserDTO();
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(userDTO.getUsername()),User::getUsername,userDTO.getUsername());
        lambdaQueryWrapper.like(StringUtils.isNotBlank(userDTO.getCode()),User::getCode,userDTO.getCode());
        lambdaQueryWrapper.like(StringUtils.isNotBlank(userDTO.getGender()),User::getGender,userDTO.getGender());
        lambdaQueryWrapper.like(StringUtils.isNotBlank(userDTO.getClazz()),User::getClazz,userDTO.getClazz());
        lambdaQueryWrapper.like(StringUtils.isNotBlank(userDTO.getMajor()),User::getMajor,userDTO.getMajor());
        lambdaQueryWrapper.like(StringUtils.isNotBlank(userDTO.getAcademy()),User::getAcademy,userDTO.getAcademy());
        lambdaQueryWrapper.like(StringUtils.isNotBlank(userDTO.getDuty()),User::getDuty,userDTO.getDuty());
        lambdaQueryWrapper.like(StringUtils.isNotBlank(userDTO.getStatus()),User::getStatus,userDTO.getStatus());
        return userMapper.selectPage(page,lambdaQueryWrapper);
    }

    @Override
    public Integer addUser(User user) {
        user.setStatus("1");
        return userMapper.insert(user);
    }



    @Override
    public Integer updateUser(User user) {
        return userMapper.updateById(user);
    }

    @Override
    public Integer deleteUser(Integer id) {
        return userMapper.deleteById(id);
    }

    @Override
    public void batchDelete(List<Integer> ids) {

        userMapper.deleteBatchIds(ids);
    }

    @Override
    public CountUserDTO count() {
        CountUserDTO countUserDTO = new CountUserDTO();
        LambdaQueryWrapper<User> queryWrapper =new LambdaQueryWrapper<>();
        Integer total = userMapper.selectCount(queryWrapper);
        countUserDTO.setTotal(total);
        queryWrapper.eq(User::getStatus,"1");
        Integer enable = userMapper.selectCount(queryWrapper);
        countUserDTO.setEnable(enable);
        return countUserDTO;
    }
}
