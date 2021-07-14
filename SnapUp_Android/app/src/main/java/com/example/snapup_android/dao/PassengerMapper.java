package com.example.snapup_android.dao;

import com.example.snapup_android.pojo.Passenger;
import org.apache.ibatis.annotations.Param;

public interface PassengerMapper {
    //创建一个乘客信息
    public void createPassenger(Passenger passenger);
    //通过用户名查询乘客信息
    public Passenger findPassengerByName(String username);
    //更新用户的类型：
    public int updatePassengerType(@Param("username")String username, @Param("identity_type") char identity_id);
    //删除一个乘客信息
    public int deletePassenger(String username);
}
