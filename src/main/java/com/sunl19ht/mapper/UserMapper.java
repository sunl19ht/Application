package com.sunl19ht.mapper;

import com.sunl19ht.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User login(String nickname, String password);
}
