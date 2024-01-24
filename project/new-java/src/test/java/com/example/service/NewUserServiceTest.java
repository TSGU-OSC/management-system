package com.example.service;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class NewUserServiceTest {

    @Resource
    NewUserService newUserService;
    @Test
    void testAddUser(){
        NewUser user = new NewUser();
        user.setUsername("test");
        user.setUserAccount("123");
        user.setAvatarUrl("C:\\Users\\lenovo\\Pictures\\Saved Pictures\\wallhaven-9mvd9k.jpg");
        user.setGender(1);
        user.setUserPassword("123");
        user.setPhone("123");
        user.setEmail("123");
        boolean result = newUserService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);

    }

    @Test
    void testUserRegister(){
        String userAccount = "";
        String userPassword = "12345678";
        String checkPassword= "12345678";
        String planetCode= "12";
        long result = newUserService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1, result);
        userAccount="lwy";
        result = newUserService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1, result);
        userAccount="liwy";
        userPassword="123456";
        result = newUserService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1, result);
        userAccount="li wy";
        userPassword="12345678";
        checkPassword="12345678";
        result = newUserService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1, result);
        userAccount="liwy";
        checkPassword="123456789";
        result = newUserService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1, result);
        userAccount="test";
        checkPassword="12345678";
        result = newUserService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1, result);
        userAccount="1234";
        result = newUserService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertTrue(result>=0);
    }

    @Test
    public void testSearchUsersByTags(){
        List<String> list= Arrays.asList("Java","Python");
        List<NewUser> userList = newUserService.searchUsersByTags(list);
        Assert.assertNotNull(userList);
    }
}