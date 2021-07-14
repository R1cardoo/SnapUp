package com.example.snapup_android.dao;

import com.example.snapup_android.pojo.ValueAdded;
import org.apache.ibatis.annotations.Param;

public interface ValueAddedMapper {
    //创建一个增值服务：
    public boolean createValueAdded(ValueAdded valueAdded);
    //查询一个增值服务：
    public ValueAdded findValueAdded(@Param("username") String username, @Param("run_serial") int run_serial);
    //修改一个增值服务：
    public boolean updateValueAdded(ValueAdded valueAdded);
}
