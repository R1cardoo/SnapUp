package com.snapup.service;

import com.snapup.pojo.Passenger;

public interface PassengerService {
    //给出用户名，乘客类型，建立乘客：
    public int createPassenger(String username, char identity_type);
    //更新乘客类型：
    public int updatePassengerType(String username, char identity_type);

}
