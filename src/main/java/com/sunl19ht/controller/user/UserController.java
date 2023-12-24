package com.sunl19ht.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.sunl19ht.pojo.Result;
import com.sunl19ht.pojo.User;
import com.sunl19ht.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserStatus userStatus;

    @PostMapping("close")
    public Result close(@RequestBody User user) {
        userStatus.setUserStatus(user.getNickname(), user.getStatus());
        return Result.success();
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody User user) {
        Integer status = userStatus.getUserStatus(user.getNickname());
        String userString = null;
        if (status == null || status == 0) {    //可以登录
            User resultUser = userService.login(user.getNickname(), user.getPassword());
            if (resultUser == null) {
                return Result.error("用户名或密码错误！");
            }
            userStatus.setUserStatus(user.getNickname(), 1);
            userString = JSONObject.toJSONString(resultUser);
        } else {
            return Result.error("当前用户已在线！");
        }
        return Result.success(userString);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        userService.register(user.getNickname(), user.getPassword());
        return Result.success();
    }
}
