package com.snapup.service;

import com.snapup.dao.PassengerMapper;
import com.snapup.dao.UserMapper;
import com.snapup.pojo.Passenger;

public class PassengerServiceImpl implements PassengerService{
    private PassengerMapper passengerMapper;
    private UserMapper userMapper;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void setPassengerMapper(PassengerMapper passengerMapper) {
        this.passengerMapper = passengerMapper;
    }

    public int createPassenger(String username, char identity_type) {
        if(passengerMapper.findPassengerByName(username) == null){
            if(userMapper.findUserByUsername(username) != null){
                String identity_id = userMapper.findUserByUsername(username).getIdentity_id();
                String name = userMapper.findUserByUsername(username).getName();
                String tele = userMapper.findUserByUsername(username).getTele();
                passengerMapper.createPassenger(new Passenger(identity_id, username, identity_type, name, tele));
                return 0;//创建成功
            }
        }
       return 1;//创建不成功
    }

    public int updatePassengerType(String username, char identity_type) {
        if(passengerMapper.findPassengerByName(username) != null){
            passengerMapper.updatePassengerType(username, identity_type);
            return 0;
        }
        return 1;//更新失败
    }
}
