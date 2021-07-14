package com.example.snapup_android.service;


import com.example.snapup_android.dao.StationMapper;
import com.example.snapup_android.dao.StationOnLineMapper;
import com.example.snapup_android.pojo.Station;
import com.example.snapup_android.pojo.Station_on_line;

import java.util.ArrayList;
import java.util.List;

public class StationOnLineServiceImpl implements StationOnLineService{
    private StationOnLineMapper stationOnLineMapper;
    private StationMapper stationMapper;

    public void setStationMapper(StationMapper stationMapper) {
        this.stationMapper = stationMapper;
    }
    public void setStationOnLineMapper(StationOnLineMapper stationOnLineMapper) {
        this.stationOnLineMapper = stationOnLineMapper;
    }

    public List<String> getTrainLine(String depart_station_code, String arrival_station_code) {
        List<String> resultTrainLines = new ArrayList<String>();
        List<Station_on_line> depart_stations = stationOnLineMapper.findStationOnLineByStation(depart_station_code);
        List<Station_on_line> arrival_stations = stationOnLineMapper.findStationOnLineByStation(arrival_station_code);
        for(Station_on_line station_on_line_start: depart_stations){
            for(Station_on_line station_on_line_end: arrival_stations){
                if(station_on_line_start.getRun_code().equals(station_on_line_end.getRun_code()) &&
                        station_on_line_start.getStation_idx() < station_on_line_end.getStation_idx() &&
                        station_on_line_start.getStation_code().equals(getStartStation(station_on_line_start.getRun_code())) &&
                        station_on_line_end.getStation_code().equals(getEndStation(station_on_line_end.getRun_code()))){
                            resultTrainLines.add(station_on_line_end.getRun_code());

                }
            }
        }

        return resultTrainLines;
    }

    public List<String> getTrainLine2(String depart_station_code, String arrival_station_code) {
        List<String> resultTrainLines = new ArrayList<String>();
        List<Station_on_line> depart_stations = stationOnLineMapper.findStationOnLineByStation(depart_station_code);
        List<Station_on_line> arrival_stations = stationOnLineMapper.findStationOnLineByStation(arrival_station_code);
        for(Station_on_line station_on_line_start: depart_stations){
            for(Station_on_line station_on_line_end: arrival_stations){
                if(station_on_line_start.getRun_code().equals(station_on_line_end.getRun_code()) &&
                    station_on_line_start.getStation_idx() < station_on_line_end.getStation_idx()){
                    resultTrainLines.add(station_on_line_end.getRun_code());
                }
            }
        }

        return resultTrainLines;
    }

    public String getStartStation(String run_code) {
        List<Station_on_line> stations = stationOnLineMapper.findStationByRunCode(run_code);
        for(Station_on_line station: stations){
            if(station.getStation_idx() == 1){
                return (String) station.getStation_code();
            }
        }
        return null;
    }

    public String getEndStation(String run_code) {

        List<Station_on_line> stations = stationOnLineMapper.findStationByRunCode(run_code);
        int station_num = stations.size();
        for(Station_on_line station: stations){
            if (station.getStation_idx() == station_num){
                return (String) station.getStation_code();
            }
        }
        return null;
    }

    public List<Station> getAllStation(String run_code) {
        List<Station_on_line> station_on_lines = stationOnLineMapper.findStationByRunCode(run_code);
        List<Station> stations = new ArrayList<Station>();
        for(Station_on_line station_on_line: station_on_lines){
            stations.add(stationMapper.findStationByCode((String) station_on_line.getStation_code()));
        }
        return stations;
    }

    public List<Station> getPassStation(String run_code, String depart_station_name, String arrival_station_name) {
        List<Station_on_line> allStationOnLines = stationOnLineMapper.findStationByRunCode(run_code);
        List<Station> passStations = new ArrayList<Station>();
        Station depart_station = stationMapper.findStationByName(depart_station_name);
        Station arrival_station = stationMapper.findStationByName(arrival_station_name);
        int depart_station_idx = stationOnLineMapper.findStationIdx(run_code, depart_station.getCode());
        int arrival_station_idx = stationOnLineMapper.findStationIdx(run_code, arrival_station.getCode());
        for(Station_on_line station_on_line: allStationOnLines){
            if(depart_station_idx <= station_on_line.getStation_idx() && station_on_line.getStation_idx() <= arrival_station_idx){
                passStations.add(stationMapper.findStationByCode((String) station_on_line.getStation_code()));
            }
        }
        return passStations;
    }
}
