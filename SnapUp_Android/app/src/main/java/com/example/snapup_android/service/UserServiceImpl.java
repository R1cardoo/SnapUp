package com.example.snapup_android.service;

import com.example.snapup_android.dao.UserMapper;
import com.example.snapup_android.pojo.User;

public class UserServiceImpl implements UserService{
    private UserMapper userMapper;
    public void setUserMapper(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    public boolean registerUser(String username, String identity_id, char gender, String name, String tele, String mail, String pwd, String nickname) {
        if (userMapper.findUserByUsername(username) == null){
            User user = new User(username, identity_id, gender, name, tele, mail, pwd, nickname);
            userMapper.createUser(user);
            return true;
        }
        return false;
    }

    public boolean isRegistered(String username) {
        return userMapper.findUserByUsername(username) != null;
    }

    public boolean checkPassword(String username, String pwd) {
        User user = userMapper.findUserByUsername(username);
        //TODO: 加密
        return user.getPassword().equals(pwd);
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
            return user.getPassword();
        }
        return null;
    }

    public boolean updateUserInfo(User user) {
        return userMapper.updateUser(user);
    }


}
