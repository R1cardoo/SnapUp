package com.snapup.service;

import com.snapup.pojo.Station_on_line;

import java.sql.Time;
import java.util.List;

public interface TimeTableService {
    //根据车次和站点编号查询列车到达时间：
    public Time getArrivalTime(String num_code, String station_code);
    //根据车次和站点编号查询列车离开时间：
    public Time getDepartTime(String num_code, String station_code);
}
