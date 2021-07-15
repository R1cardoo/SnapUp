package com.snapup.dao;

import com.snapup.pojo.TimeTable;
import org.apache.ibatis.annotations.Param;

import java.sql.Time;
import java.util.List;

public interface TimeTableMapper {
    //根据站点编码查询：
    public List<TimeTable> findTimeTableByCode(String station_code);
    //根据车次查询：
    public List<TimeTable> findTimeTableByTrainRun(String num_code);
    //根据站点和车次查询TimeTable
    public TimeTable findTimeTable(@Param("num_code") String num_code, @Param("station_code") String station_code);
    //根据时间查询经该时间的所有TimeTable信息：
    public List<TimeTable> findTimeTableByDepartTime(Time depart_time);
    //根据时间查询经该时间的所有TimeTable信息：
    public List<TimeTable> findTimeTableByArriveTime(Time arrive_time);
}
