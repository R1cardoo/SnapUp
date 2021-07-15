package com.snapup.service;


import com.snapup.dao.StationMapper;
import com.snapup.dao.StationOnLineMapper;
import com.snapup.dao.TimeTableMapper;
import com.snapup.dao.TrainRunMapper;
import com.snapup.pojo.Station;
import com.snapup.pojo.Station_on_line;
import com.snapup.pojo.TimeTable;
import com.snapup.pojo.TrainRun;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StationOnLineServiceImpl implements StationOnLineService{
    private StationOnLineMapper stationOnLineMapper;
    private StationMapper stationMapper;
    private TrainRunMapper trainRunMapper;
    private TimeTableMapper timeTableMapper;

    public void setStationMapper(StationMapper stationMapper) {
        this.stationMapper = stationMapper;
    }
    public void setStationOnLineMapper(StationOnLineMapper stationOnLineMapper) {
        this.stationOnLineMapper = stationOnLineMapper;
    }
    public void setTrainRunMapper(TrainRunMapper trainRunMapper) {
        this.trainRunMapper = trainRunMapper;
    }
    public void setTimeTableMapper(TimeTableMapper timeTableMapper) {
        this.timeTableMapper = timeTableMapper;
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

    public List<String> getTrainLineByDepartStation(String depart_station_code) {
        List<String> resultTrainLines = new ArrayList<String>();
        List<Station_on_line> depart_stations = stationOnLineMapper.findStationOnLineByStation(depart_station_code);
        for (Station_on_line sol : depart_stations) {
            if (sol.getStation_idx() == 1) {
                resultTrainLines.add(sol.getRun_code());
            }
        }
        return resultTrainLines;
    }

    public List<String> getTrainLineByArriveStation(String arrive_station_code) {
        List<String> resultTrainLines = new ArrayList<String>();
        List<Station_on_line> arrive_stations = stationOnLineMapper.findStationOnLineByStation(arrive_station_code);
        for (Station_on_line sol : arrive_stations) {
            String trainRunCode = sol.getRun_code();
            TrainRun trainRun = trainRunMapper.findTrainRunByCode(trainRunCode);
            if (sol.getStation_idx() == trainRun.getStation_num()) {
                resultTrainLines.add(trainRunCode);
            }
        }
        return resultTrainLines;
    }

    public List<String> getTrainLineByDepartTime(Date depart_station_time) {
        List<String> resultTrainLines = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String timeString = sdf.format(depart_station_time);
        Time time = Time.valueOf(timeString + ":00");
        List<TimeTable> ltt = timeTableMapper.findTimeTableByDepartTime(time);
        for (TimeTable tt : ltt) {
            String stationCode = tt.getStation_code();
            String trainRunCode = tt.getNum_code();
            int idx = stationOnLineMapper.findStationIdx(trainRunCode, stationCode);
            if (idx == 1) {
                resultTrainLines.add(trainRunCode);
            }
        }
        return resultTrainLines;
    }

    public List<String> getTrainLineByArriveTime(Date arrival_station_time) {
        List<String> resultTrainLines = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String timeString = sdf.format(arrival_station_time);
        Time time = Time.valueOf(timeString + ":00");
        List<TimeTable> ltt = timeTableMapper.findTimeTableByArriveTime(time);
        for (TimeTable tt : ltt) {
            String stationCode = tt.getStation_code();
            String trainRunCode = tt.getNum_code();
            TrainRun trainRun = trainRunMapper.findTrainRunByCode(trainRunCode);
            int idx = stationOnLineMapper.findStationIdx(trainRunCode, stationCode);
            if (idx == trainRun.getStation_num()) {
                resultTrainLines.add(trainRunCode);
            }
        }
        return resultTrainLines;
    }

    public String getStartStation(String run_code) {
        List<Station_on_line> stations = stationOnLineMapper.findStationByRunCode(run_code);
        for(Station_on_line station: stations){
            if(station.getStation_idx() == 1){
                return station.getStation_code();
            }
        }
        return null;
    }

    public String getEndStation(String run_code) {
        List<Station_on_line> stations = stationOnLineMapper.findStationByRunCode(run_code);
        int station_num = stations.size();
        for(Station_on_line station: stations){
            if (station.getStation_idx() == station_num){
                return station.getStation_code();
            }
        }
        return null;
    }

    public List<Station> getAllStation(String run_code) {
        List<Station_on_line> station_on_lines = stationOnLineMapper.findStationByRunCode(run_code);
        List<Station> stations = new ArrayList<Station>();
        for(Station_on_line station_on_line: station_on_lines){
            stations.add(stationMapper.findStationByCode(station_on_line.getStation_code()));
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
                passStations.add(stationMapper.findStationByCode(station_on_line.getStation_code()));
            }
        }
        return passStations;
    }

    public String getOneStation(String run_code, int station_idx) {
        return stationOnLineMapper.findStationCode(run_code, station_idx);
    }

    public void delStation(String run_code) {
        stationOnLineMapper.deleteLine(run_code);
    }

    public void addStation(String run_code, int station_idx, String station_code) {
        stationOnLineMapper.createStationOnLine(run_code, station_idx, station_code);
    }
}
