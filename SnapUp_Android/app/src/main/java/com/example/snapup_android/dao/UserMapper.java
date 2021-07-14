package com.example.snapup_android.dao;

import com.example.snapup_android.pojo.User;

public interface UserMapper {
    //创建一个用户
    public void createUser(User user);
    //通过用户ID查询用户
    public User findUserByUsername(String username);
    //更新一个用户
    public boolean updateUser(User user);
}
