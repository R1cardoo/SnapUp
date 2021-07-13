package com.snapup.service;

public interface OrderService {
    //创建一个订单：
    public void createOrder(int run_serial, int coach_id, int seat_id, int depart_station_idx, int arrival_station_idx, String username);
    //查看订单：

    //删除订单：

}
