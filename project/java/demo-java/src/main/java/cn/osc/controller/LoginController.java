package cn.osc.controller;

import cn.osc.dto.LoginDTO;
import cn.osc.entity.User;
import cn.osc.result.Result;
import cn.osc.service.LoginService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @Author: fsq
 * @Date: 2024/1/3
 * @Description: TODO
 **/
@Api(tags = "登录接口")
@CrossOrigin
@RestController()
public class LoginController {
    @Autowired
    LoginService loginService;

    @PostMapping(value = "/api/login")
    public Result login(@RequestBody LoginDTO loginDTO){

        return loginService.login(loginDTO.getUsername(), loginDTO.getPassword(), loginDTO.getCode(),
                loginDTO.getUuid());

//        return loginService.login(loginDTO);
    }

    @PostMapping(value = "/api/register")
    public Result register(@RequestBody User user){
        return loginService.register(user);
    }


}
