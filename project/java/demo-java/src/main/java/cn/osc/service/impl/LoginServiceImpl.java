package cn.osc.service.impl;

import cn.osc.config.CustomException;
import cn.osc.constants.Constants;
import cn.osc.core.RedisCache;
import cn.osc.dto.LoginDTO;
import cn.osc.entity.User;
import cn.osc.mapper.UserMapper;
import cn.osc.result.Result;
import cn.osc.service.LoginService;
import cn.osc.service.UserService;
import cn.osc.vo.LoginVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

/**
 * @Author: fsq
 * @Date: 2024/1/3
 * @Description:
 **/
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisCache redisCache;
//    @Override
//    public Result login(LoginDTO loginDTO) {
//        if (StringUtils.isEmpty(loginDTO.getUsername())){
//            return new Result(400,"账号不能为空","");
//        }
//        if (StringUtils.isEmpty(loginDTO.getPassword())){
//            return new Result(400,"密码不能为空","");
//        }
//        //通过登录名查询用户
//        QueryWrapper<User> wrapper = new QueryWrapper();
//        wrapper.eq("username", loginDTO.getUsername());
//        User uer=userMapper.selectOne(wrapper);
//        //比较密码
//        if (uer!=null&&uer.getPassword().equals(loginDTO.getPassword())){
//            LoginVO loginVO=new LoginVO();
//            loginVO.setId(uer.getId());
//            //这里token直接用一个uuid
//            //使用jwt的情况下，会生成一个jwt token,jwt token里会包含用户的信息
//            loginVO.setToken(UUID.randomUUID().toString());
//            loginVO.setUser(uer);
//            return new Result(200,"",loginVO);
//        }
//        return new Result(401,"登录失败","");
//    }

    @Override
    public Result login(String username, String password, String code, String uuid) {

        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (!code.equalsIgnoreCase(captcha))
        {
           return  new Result(401,"验证码错误！","");
        }
        if (StringUtils.isEmpty(username)){
            return new Result(402,"账号不能为空","");
        }
        if (StringUtils.isEmpty(password)){
            return new Result(403,"密码不能为空","");
        }
        //通过登录名查询用户
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("username", username);
        User user=userMapper.selectOne(wrapper);
        //比较密码
        if (user!=null&&user.getPassword().equals(password)){
            LoginVO loginVO=new LoginVO();
            loginVO.setId(user.getId());
            //这里token直接用一个uuid
            //使用jwt的情况下，会生成一个jwt token,jwt token里会包含用户的信息
            loginVO.setToken(UUID.randomUUID().toString());
            user.setPassword("");
            loginVO.setUser(user);

//            session.setAttribute("userId",user.getId());
            return new Result(200,"",loginVO);
        }
        return new Result(401,"登录失败，账号或密码错误","");
    }

    @Override
    public Result register(User user) {
        try {
            user.setStatus("1");
            userMapper.insert(user);
        } catch (Exception e) {
            return new Result(400,"注册失败！",e);
        }
        return new Result(200,"注册成功","");
    }
}
