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

    @PostMapping("/login")
    public Result<String> login(@RequestBody User user) {
        User resultUser = userService.login(user.getNickname(), user.getPassword());
        if (resultUser == null) {
            return Result.error("用户名或密码错误！");
        }
        String userString = JSONObject.toJSONString(resultUser);
        return Result.success(userString);
    }
}
