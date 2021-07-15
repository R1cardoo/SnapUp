package com.example.snapup_android.service;

import com.example.snapup_android.pojo.Station;

import java.util.List;

public interface StationOnLineService {
    //购票者可以输入旅程信息，返回线路：
    public List<String> getTrainLine(String run_code);

    public List<String> getTrainLine(String depart_station_code, String arrival_station_code);
    //购票者可以输入旅程信息，返回线路，包括中间站:
    public List<String> getTrainLine2(String depart_station_code, String arrival_station_code);
    //查询一个车次的始发站
    public String getStartStation(String run_code);
    //查询一个车次的终点站
    public String getEndStation(String run_code);
    //查询一个车次的所有途径站点
    public List<Station> getAllStation(String run_code);
    //查询经停站：
    public List<Station> getPassStation(String run_code, String depart_station_name, String arrival_station_name);

}
