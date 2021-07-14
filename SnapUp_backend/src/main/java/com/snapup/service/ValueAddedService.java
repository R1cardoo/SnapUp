package com.snapup.service;

import com.snapup.pojo.ValueAdded;

public interface ValueAddedService {
    //创建一个增值服务
    public boolean createValueAdded(ValueAdded valueAdded);
    //查询一个增值服务
    public ValueAdded findValueAdded(String username, int run_serial);
    //更新增值服务：
    public boolean updateValueAdded(ValueAdded valueAdded);
}
