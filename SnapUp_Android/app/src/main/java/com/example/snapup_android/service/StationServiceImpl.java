package com.example.snapup_android.service;

import com.example.snapup_android.dao.StationMapper;
import com.example.snapup_android.pojo.Station;

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
