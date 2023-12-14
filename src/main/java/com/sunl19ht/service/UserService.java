package com.sunl19ht.service;

import com.sunl19ht.pojo.User;

public interface UserService {
    public User login(String nickname, String password);

    void register(String nickname, String password);
}
