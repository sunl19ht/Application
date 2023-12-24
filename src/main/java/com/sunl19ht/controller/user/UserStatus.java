package com.sunl19ht.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserStatus {
    @Autowired
    private RedisTemplate redisTemplate;

    public void setUserStatus(String nickName, Integer status) {
        redisTemplate.opsForValue().set(nickName + "_status", status);
    }

    public Integer getUserStatus(String nickName) {
        return (Integer) redisTemplate.opsForValue().get(nickName + "_status");
    }
}
