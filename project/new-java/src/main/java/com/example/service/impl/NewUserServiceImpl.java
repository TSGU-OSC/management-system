package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.ErrorCode;
import com.example.exception.BusinessException;
import com.example.mapper.NewUserMapper;
import com.example.model.domain.NewUser;
import com.example.service.NewUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.content.UserContent.USER_LOGIN_STATE;

/**
 * 用户服务实现类
 *
 * @author lwy
 */
@Service
@Slf4j
public class NewUserServiceImpl extends ServiceImpl<NewUserMapper, NewUser> implements NewUserService {

    @Resource
    private NewUserMapper userMapper;
    /**
     * 对密码进行加盐加密（加盐就是让密码加密后更复杂）
     */
    private static final String SALT = "lwy";


/*    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        // 密码，账号，二次校验密码 不能为空
        if (StringUtils.isAnyBlank(userPassword, userAccount, checkPassword, planetCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        // 账号长度不小于4
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号过短");
        }
        // 密码和二次校验密码 长度不小于8
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码过短");
        }
        // 星球编号长度不能大于5
        if (planetCode.length() > 5) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "星球编号过长");
        }
        // 账号不能含有特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不合法");
        }
        // 密码与二次校验密码需一致
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码不一致");
        }
        // 账号不可重复
        QueryWrapper<NewUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }
        // 星球编号不可重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode", planetCode);
        count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "新球编号重复");
        }
        // 加密密码
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 建立用户实体
        NewUser user = new NewUser();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        // 存储用户信息
        boolean result = this.save(user);
        // 若返回null则不利于后续处理
        if (!result) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "注册失败");
        }
        // 用户注册成功
        return user.getId();
    }*/

    /**
     * 用户登录
     *
     * @param code     用户学号
     * @param password 用户密码
     * @param request
     * @return
     */
    @Override
    public NewUser userLogin(String code, String password, HttpServletRequest request) {
        // 密码，账号不能为空
        if (StringUtils.isAnyBlank(password, code)) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "学号或密码不能为空");
        }
        // 学号长度为11位
        if (code.length() != 11) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "学号格式错误");
        }
        // 密码长度不小于8
        if (password.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码过短");
        }
        // 密码不能含有特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
        Matcher matcher = Pattern.compile(validPattern).matcher(password);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码包含特殊字符");
        }
        // 对密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        // 尝试登录
        QueryWrapper<NewUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        queryWrapper.eq("password", encryptPassword);
        NewUser user = userMapper.selectOne(queryWrapper);
        //用户不存在
        if (user == null) {
            log.info("在数据库中找不到学号和密码匹配的数据");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }
        // 用户被封号
        if (user.getStatus() == 1) {
            log.info("用户已被封号");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户已被封号");
        }
        //用户脱敏
        NewUser safetyUser = getSafetyUser(user);
        //记录用户的登录状态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);

        return safetyUser;
    }

    /**
     * 用户登出
     *
     * @param request
     * @return
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    /**
     * 用户脱敏
     *
     * @param originUser 脱敏前的用户
     * @return 脱敏后用户信息
     */
    @Override
    public NewUser getSafetyUser(NewUser originUser) {
        if (originUser == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户为空");
        }
        NewUser safetyUser = new NewUser();
        safetyUser.setId(originUser.getId());
        safetyUser.setCode(originUser.getCode());
        safetyUser.setAvator(originUser.getAvator());
        safetyUser.setName(originUser.getName());
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

}




