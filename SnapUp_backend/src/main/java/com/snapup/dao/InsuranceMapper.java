package com.snapup.dao;

import com.snapup.pojo.Insurance;

public interface InsuranceMapper {
    //创建一个保险信息
    public void createInsurance(Insurance insurance);
    //通过订单号查询一个保险信息：
    public Insurance findInsuranceByOrder(String order_id);
    //删除一个保险信息(通过订单号)
    public int deleteInsurance(String order_id);
}
