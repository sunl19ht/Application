package com.sunl19ht.service.impl;

import com.sunl19ht.mapper.UserMapper;
import com.sunl19ht.pojo.User;
import com.sunl19ht.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String nickname, String password) {
        return userMapper.login(nickname, password);
    }

    @Override
    public void register(String nickname, String password) {
        userMapper.register(nickname, password);
    }
}
