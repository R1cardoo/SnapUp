package com.snapup.service;

import com.snapup.pojo.Order;
import com.snapup.pojo.ValueAdded;

import java.util.List;

public interface OrderService {
    //创建一个订单：
    public int createOrderLow(int run_serial, int coach_id, int seat_id, int depart_station_idx, int arrival_station_idx, String username, String passenger_id, float price, char seat_type);
    //查看订单：根据用户用户名和乘客 TODO:乘客ID和用户ID
    public Order findOrder(int run_serial, String username, String passenger_id);
    //删除订单：
    public int deleteOrder(int run_serial, String username, String passenger_id);
    //查询一个用户的所有订单：
    public List<Order> findAllUserOrder(String username);
    //创建一个订单顶层：
    public float createOrder(int run_serial, String depart_station_name, String arrival_station_name, char seat_type, String username);
    //生成车厢和座位信息：
    public List<Integer>generateCoachAndSeat(int run_serial, char seat_type);
    //查看是否已经订单
    public boolean checkValid(String username, int run_serial);
    //根据增值服务更新订单
    public void updateOrder(ValueAdded valueAdded);
}
