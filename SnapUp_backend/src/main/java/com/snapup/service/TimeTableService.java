package com.snapup.service;

import java.sql.Time;

public interface TimeTableService {
    //根据车次和站点编号查询列车到达时间：
    public Time getArrivalTime(String num_code, String station_code);
    //根据车次和站点编号查询列车离开时间：
    public Time getDepartTime(String num_code, String station_code);

    public void delLine(String num_code);
}
