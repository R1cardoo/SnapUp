package com.example.snapup_android.dao;

import com.example.snapup_android.pojo.Seat;
import org.apache.ibatis.annotations.Param;

public interface SeatMapper {
    //创建座位情况：
    public void createSeat(Seat seat);
    //查询该车次上的所有座位：
    public Seat findAllSeat(String num_code);
    //查询该车次某种座位的数量：
    public int findSeatNum(@Param("num_code") String num_code, @Param("seat_type") char seat_type);
    //查询某一座位类型：
    public char findSeatType(String num_code, int coach_idx, int seat_idx);
}
