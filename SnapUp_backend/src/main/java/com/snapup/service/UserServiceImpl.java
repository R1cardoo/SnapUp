package com.snapup.service;

import com.snapup.dao.UserMapper;
import com.snapup.pojo.User;

public class UserServiceImpl implements UserService{
    private UserMapper userMapper;
    public void setUserMapper(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    public void registerUser(String username, String identity_id, char gender, String name, String tele, String mail, String pwd, String nickname) {
        if (userMapper.findUserByUsername(username) == null){
            User user = new User(username, identity_id, gender, name, tele, mail, pwd, nickname);
            userMapper.createUser(user);
        }
    }

    public boolean isRegistered(String username) {
        return userMapper.findUserByUsername(username) != null;
    }

    public boolean checkPassword(String username, String pwd) {
        User user = userMapper.findUserByUsername(username);
        //TODO: 加密
        return user.getPwd().equals(pwd);
    }

    public User getUserInstance(String username, String pwd) {
        if(isRegistered(username)){
            if(checkPassword(username, pwd)){
                return userMapper.findUserByUsername(username);
            }
        }
        return null;
    }

    public String getPwd(String username) {
        User user = userMapper.findUserByUsername(username);
        if (user != null){
            return user.getPwd();
        }
        return null;
    }
}
