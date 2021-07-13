package com.snapup.service;

import com.snapup.pojo.Station;

public interface StationService {
    //根据名称查询站点信息：
    Station getStationByName(String name);
    //根据站点编号查询站点信息：
    Station getStationByCode(String code);
}
