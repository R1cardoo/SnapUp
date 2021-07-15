package com.snapup.service;

import com.snapup.pojo.Station;
import com.snapup.pojo.TrainRun;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface StationOnLineService {
    //购票者可以输入旅程信息，返回线路：
    public List<String> getTrainLine(String depart_station_code, String arrival_station_code);
    //购票者可以输入始发站，返回线路
    public List<String> getTrainLineByDepartStation(String depart_station_code);
    //购票者可以输入终点站，返回线路
    public List<String> getTrainLineByArriveStation(String arrival_station_code);
    //购票者可以输入始发时间，返回线路
    public List<String> getTrainLineByDepartTime(Date depart_station_time);
    //购票者可以输入终点时间，返回线路
    public List<String> getTrainLineByArriveTime(Date arrival_station_time);
    //购票者可以输入旅程信息，返回线路，包括中间站:
    public List<String> getTrainLine2(String depart_station_code, String arrival_station_code);
    //查询一个车次的始发站
    public String getStartStation(String run_code);
    //查询一个车次的终点站
    public String getEndStation(String run_code);
    // 返回第idx个站的code
    public String getOneStation(String run_code, int station_idx);
    //查询一个车次的所有途径站点
    public List<Station> getAllStation(String run_code);
    //查询经停站：
    public List<Station> getPassStation(String run_code, String depart_station_name, String arrival_station_name);

    public void delStation(String run_code);
}
