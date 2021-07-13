package com.snapup.service;

import com.snapup.pojo.User;

public interface UserService {
    //注册用户
    void registerUser(String username, String identity_id,
                      char gender, String name, String tele,
                      String mail, String pwd, String nickname);
    //判断是否已经注册
    boolean isRegistered(String username);
    //检查登录密码
    boolean checkPassword(String username, String pwd);
    //通过用户名、密码返回用户对象
    User getUserInstance(String username, String pwd);
    //通过用户名返回密码(找回密码)
    String getPwd(String username);
}
