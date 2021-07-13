package com.snapup.dao;

import com.snapup.pojo.Station;

public interface StationMapper {
    // 通过站点编号查询站点
    public Station findStationByCode(String code);
    // 通过站名称查询站点
    public Station findStationByName(String name);
}
