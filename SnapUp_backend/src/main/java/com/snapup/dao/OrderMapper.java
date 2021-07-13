package com.snapup.dao;

import com.snapup.pojo.Order;

import java.util.List;

public interface OrderMapper {
    //创建一个订单信息：
    public void createOrder(Order order);
    //根据订单号查询订单
    public Order findOrderByTicketId(int ticket_id);
    //查询改用户的所有订单
    public List<Order> findOrderByUsername(String username);
    //修改订单
    public void updateOrder(Order order);
    //删除订单
    public int deleteOrder(int ticket_id);
}
