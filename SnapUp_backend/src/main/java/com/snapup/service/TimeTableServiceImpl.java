package com.snapup.service;

import com.snapup.dao.TimeTableMapper;
import com.snapup.pojo.TimeTable;

import java.sql.Time;

public class TimeTableServiceImpl implements TimeTableService{
    private TimeTableMapper timeTableMapper;

    public void setTimeTableMapper(TimeTableMapper timeTableMapper) {
        this.timeTableMapper = timeTableMapper;
    }

    public Time getArrivalTime(String num_code, String station_code) {
        TimeTable timeTable = timeTableMapper.findTimeTable(num_code, station_code);
        if(timeTable == null) return null;
        return timeTable.getArrival_time();
    }

    public Time getDepartTime(String num_code, String station_code) {
        TimeTable timeTable = timeTableMapper.findTimeTable(num_code, station_code);
        if(timeTable == null) return null;
        return timeTable.getDeparture_time();
    }

    public void delLine(String num_code) {
        timeTableMapper.delLine(num_code);
    }
}
