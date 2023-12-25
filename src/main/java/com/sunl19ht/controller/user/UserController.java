package com.sunl19ht.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.sunl19ht.pojo.Result;
import com.sunl19ht.pojo.User;
import com.sunl19ht.service.UserService;
import com.sunl19ht.utils.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        String userString;
        if (status == null || status == 0) {    //可以登录
            User resultUser = userService.login(user.getNickname(), user.getPassword());
            if (resultUser == null) {
                return Result.error("用户名或密码错误！");
            }
            //登录成功 创建Jwt令牌
            String token = Jwts.builder()
                    .setSubject(resultUser.getUserId().toString())
                    .claim(resultUser.getNickname(), resultUser.getUserId())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 设置JWT的过期时间
                    .signWith(SignatureAlgorithm.HS256, "userToken") // 使用HS256算法和密钥进行签名
                    .compact(); // 生成最终的Token字符串
            userStatus.setUserStatus(user.getNickname(), 1);
            resultUser.setToken(token);
            userString = JSONObject.toJSONString(resultUser);
            System.err.println(token);
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
