package com.snapup.service;

import com.snapup.dao.StationMapper;
import com.snapup.pojo.Station;

public class StationServiceImpl implements StationService{
    private StationMapper stationMapper;
    public void setStationMapper(StationMapper stationMapper){
        this.stationMapper = stationMapper;
    }
    public Station getStationByName(String name) {
        return stationMapper.findStationByName(name);
    }

    public Station getStationByCode(String code) {

        return stationMapper.findStationByCode(code);
    }

    public String toString() {
        return "StationServiceImpl{" +
                "stationMapper=" + stationMapper +
                '}';
    }
}
