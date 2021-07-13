package com.snapup.dao;

import com.snapup.pojo.Station_on_line;

import java.util.List;

public interface StationOnLineMapper {
    //根据站台编号查询经该站的所有线路站点信息：
    public List<Station_on_line> findStationOnLineByStation(String station_code);
    //查询该车次的所有站点信息：
    public List<Station_on_line> findStationByRunCode(String run_code);
}
