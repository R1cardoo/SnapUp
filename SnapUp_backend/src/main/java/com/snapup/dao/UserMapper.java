package com.snapup.dao;

import com.snapup.pojo.User;

public interface UserMapper {
    //创建一个用户
    public void createUser(User user);
    //通过用户ID查询用户
    public User findUserByUsername(String username);
    //更新一个用户
    public boolean updateUser(User user);
}
