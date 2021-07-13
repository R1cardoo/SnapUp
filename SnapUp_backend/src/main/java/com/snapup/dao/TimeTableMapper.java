package com.snapup.dao;

import com.snapup.pojo.TimeTable;

public interface TimeTableMapper {
    //根据站点编码查询：
    public TimeTable findTimeTableByCode(String station_code);
    //根据车次查询：
    public TimeTable findTimeTableByTrainRun(String num_code);

}
