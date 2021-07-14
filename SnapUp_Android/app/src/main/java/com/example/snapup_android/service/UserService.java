package com.example.snapup_android.service;

import com.example.snapup_android.pojo.User;

import org.jetbrains.annotations.NotNull;

import kotlin.Unit;

public interface UserService {
    //注册用户
    boolean registerUser(String username, String identity_id,
                      char gender, String name, String tele,
                      String mail, String pwd, String nickname);
    //判断是否已经注册
    boolean isRegistered(String username);
    //检查登录密码
    boolean checkPassword(String username, String pwd);
    //通过用户名、密码返回用户对象
    public User getUserInstance(String username, String pwd);
    //通过用户名返回密码(找回密码)
    String getPwd(String username);
    //修改用户信息：
    boolean updateUserInfo(User user);
}
