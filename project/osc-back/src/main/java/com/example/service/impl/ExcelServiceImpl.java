package com.example.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.BaseContext;
import com.example.enums.ErrorCodeEnum;
import com.example.exception.BusinessException;
import com.example.mapper.UserMapper;
import com.example.model.entity.ExcelInput;
import com.example.model.entity.ExcelOutput;
import com.example.model.entity.User;
import com.example.service.ExcelService;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.constant.UserConstant.DEFAULT_USER;


/**
 * Excel服务实现类
 *
 * @author osc
 */
@Service
@Slf4j
public class ExcelServiceImpl extends ServiceImpl<UserMapper, User> implements ExcelService {

    @Resource
    UserService userService;
    public void excelInput(@NotNull List<ExcelInput> cachedDataList) {
// 鉴权
        Long currentId = BaseContext.getCurrentId();
        User currentUser = userService.getById(currentId);
        if (currentUser.getRole() <= DEFAULT_USER) {
            throw new BusinessException(ErrorCodeEnum.NO_AUTH, "权限不足");
        }
        List<User> users = new ArrayList<>();
        cachedDataList.forEach((excelInput -> {
            if (excelInput.getCode().length() != 11) {
                throw new BusinessException(ErrorCodeEnum.PARAMS_ERROR, excelInput.getCode() + "-学号格式错误");
            }
// 学号不可重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("code", excelInput.getCode());
            long count = this.count(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCodeEnum.PARAMS_ERROR, excelInput.getCode() + "-学号重复");
            }
// 分割地区
            String[] place = excelInput.getPlace().split("-");
// 建立用户实体
            User user = new User();
            user.setCode(excelInput.getCode());
            user.setPassword("12345678");
            user.setName(excelInput.getName());
            user.setGender("男".equals(excelInput.getGender()) ? 1 : 0);
            user.setPhone(excelInput.getPhone());
            user.setClazz(Integer.valueOf(excelInput.getClazz()));
            user.setMajor(excelInput.getMajor());
            user.setAcademy(excelInput.getAcademy());
            user.setProvince(place[0]);
            user.setCity(place[1]);
// 0-运营部，1-技术部，2-宣传部
            user.setDepartment("运营部".equals(excelInput.getDepartment()) ? 0 : "技术部".equals(excelInput.getDepartment()) ? 1 : 2);
            user.setIntroduction(excelInput.getIntroduction());
            users.add(user);
        }));
// 存储用户信息
        this.saveBatch(users);
    }
    /**
     * 导出用户信息
     */
    @Override
    public void excelOutput(HttpServletResponse response) {
        // 鉴权
        Long currentId = BaseContext.getCurrentId();
        User currentUser = userService.getById(currentId);
        if(currentUser.getRole()<=DEFAULT_USER){
            throw new BusinessException(ErrorCodeEnum.NO_AUTH,"权限不足");
        }
        try {
            response.setContentType("application/vnd.ms-excel");// 设置文本内省
            response.setCharacterEncoding("utf-8");// 设置字符编码
            response.setHeader("Content-disposition", "attachment;filename=demo.xlsx"); // 设置响应头
            // 忽略部分字段
            List<String> excludeList = List.of("isDeleted", "avator", "password");
            Set<String> excludeColumnFiledNames = new HashSet<>(excludeList);
            EasyExcel.write(response.getOutputStream(), ExcelOutput.class)
                    .excludeColumnFiledNames(excludeColumnFiledNames).sheet("用户信息").doWrite(() -> {
                        List<User> userList = this.list();
                        // 如果用户权限低，对查询到的用户进行脱敏
                        if (currentUser.getRole() == DEFAULT_USER) {
                            userList = userList.stream().map(userService::getSafetyUser).toList();
                        }
                        return userList;
                    }); //用io流来写入数据
        } catch (IOException e) {
            throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR, "导出失败");
        }
    }
}