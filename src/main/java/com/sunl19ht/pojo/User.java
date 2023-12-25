package com.sunl19ht.pojo;

import lombok.Data;

@Data
public class User {
    private Long userId;    //用户id
    private String nickname;    //用户昵称
    private String password;    //用户密码
    private String createdAt;   //用户时间戳
    private Integer status; //在线状态
    private String token;
}
