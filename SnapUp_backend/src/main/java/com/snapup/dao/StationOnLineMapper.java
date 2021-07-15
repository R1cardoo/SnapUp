package com.snapup.dao;

import com.snapup.pojo.Station_on_line;
import org.apache.ibatis.annotations.Param;

import java.sql.Time;
import java.util.List;

public interface StationOnLineMapper {
    //根据站台编号查询经该站的所有线路站点信息：
    public List<Station_on_line> findStationOnLineByStation(String station_code);
    //查询该车次的所有站点信息：
    public List<Station_on_line> findStationByRunCode(String run_code);
    //根据该车次的编号和站点名称查询该站点再这条车次上的索引
    public int findStationIdx(@Param("run_code") String run_code, @Param("station_code")String station_code);
    public String findStationCode(@Param("run_code") String run_code, @Param("station_idx")int station_idx);

    public void deleteLine(String run_code);

    public void createStationOnLine(@Param("run_code") String run_code, @Param("station_idx") int station_idx,
                                @Param("station_code") String station_code);
}
