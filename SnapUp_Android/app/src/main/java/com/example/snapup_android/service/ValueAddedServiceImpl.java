package com.example.snapup_android.service;

import com.example.snapup_android.dao.ValueAddedMapper;
import com.example.snapup_android.pojo.ValueAdded;

public class ValueAddedServiceImpl implements ValueAddedService{
    private ValueAddedMapper valueAddedMapper;
    private OrderService orderService;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void setValueAddedMapper(ValueAddedMapper valueAddedMapper) {
        this.valueAddedMapper = valueAddedMapper;
    }

    public boolean createValueAdded(ValueAdded valueAdded) {
        if(orderService.checkValid(valueAdded.getUsername(), valueAdded.getRun_serial())){
            if(valueAddedMapper.findValueAdded(valueAdded.getUsername(), valueAdded.getRun_serial())==null){
                valueAddedMapper.createValueAdded(valueAdded);
                return true;
            }
        }
        return false;
    }


    public ValueAdded findValueAdded(String username, int run_serial) {

        return valueAddedMapper.findValueAdded(username, run_serial);
    }

    public boolean updateValueAdded(ValueAdded valueAdded) {
        if(orderService.checkValid(valueAdded.getUsername(), valueAdded.getRun_serial())){
            if(valueAddedMapper.findValueAdded(valueAdded.getUsername(), valueAdded.getRun_serial())!=null){
                valueAddedMapper.updateValueAdded(valueAdded);
                return true;
            }
        }
        return false;
    }
}
