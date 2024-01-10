package cn.osc.controller;

import cn.osc.config.BaseContext;
import cn.osc.dto.QueryDTO;
import cn.osc.dto.UserDTO;
import cn.osc.entity.User;
import cn.osc.mapper.UserMapper;
import cn.osc.result.Result;
import cn.osc.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author fsq
 * @Date 2024/1/6
 * @Description 用户管理
 */
@Api(tags = "用户管理")
@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    /**
     * 分页查询
     * @param queryDTO
     * @return
     */
    @PostMapping("/api/user/list")
    public Result userList(@RequestBody QueryDTO queryDTO){

        return new Result(200,"",userService.selectUserPage(queryDTO));
    }

    /**
     * 添加
     * @param user
     * @return
     */
    @PostMapping("/api/user/add")
    public Result addUser(@RequestBody @Valid User user,HttpSession session){
//        if(isAdmin(session)){
            return new Result(200,"",userService.addUser(user));
//        }else{
//            return new Result(400,"没有权限","");
//        }
    }


    /**
     * 更新
     * @param user
     * @return
     */
    @PostMapping("/api/user/update")
    public Result updateUser(@RequestBody @Valid User user,HttpSession session){
//        if(isAdmin(session)) {
            return new Result(200, "", userService.updateUser(user));
//        }else{
//            return new Result(400,"没有权限","");
//        }
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/api/user/delete")
    public Result deleteUser(Integer id){
        return new Result(200,"",userService.deleteUser(id));
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/api/user/delete/batch")
    public Result batchDeleteUser(@RequestBody List<Integer> ids){
        if(ids.size()==0){
            return new Result(400,"请选择删除的用户！","");
        }
        userService.batchDelete(ids);
        return new Result(200,"","");
    }

    @GetMapping("/api/user/count")
    public Result count(){
        return new Result(200,"",userService.count());
    }


//    private boolean isAdmin(HttpSession session) {
//        String userId = (String) session.getAttribute("userId");
//        User user = userMapper.selectById(userId);
//        if(user==null){
//            return false;
//        }
//        if(user.getIsAdmin() == 1){
//            return true ;
//        }
//        return false;
//    }
}
