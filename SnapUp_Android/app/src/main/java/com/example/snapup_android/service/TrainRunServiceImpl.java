package com.example.snapup_android.service;

import com.example.snapup_android.dao.TrainRunMapper;
import com.example.snapup_android.pojo.TrainInfo;

import java.sql.Time;

public class TrainRunServiceImpl implements TrainRunService{
    private TrainRunMapper trainRunMapper;
    private StationOnLineService stationOnLineService;
    private StationService stationService;
    private TimeTableService timeTableService;

    public void setTimeTableService(TimeTableService timeTableService) {
        this.timeTableService = timeTableService;
    }

    public void setStationService(StationService stationService) {
        this.stationService = stationService;
    }

    public void setStationOnLineService(StationOnLineService stationOnLineService) {
        this.stationOnLineService = stationOnLineService;
    }

    public void setTrainRunMapper(TrainRunMapper trainRunMapper) {
        this.trainRunMapper = trainRunMapper;
    }

    public char getTrainType(String run_code) {
        return trainRunMapper.findTrainRunByCode(run_code).getType();
    }

    public int getStationNum(String run_code) {
        return trainRunMapper.findTrainRunByCode(run_code).getStation_num();
    }

    public int getCoachNum(String run_code) {
        return trainRunMapper.findTrainRunByCode(run_code).getCoach_num();
    }

    public int getSeatNum(String run_code) {
        return trainRunMapper.findTrainRunByCode(run_code).getSeat_num();
    }

    public TrainInfo getTrainInfo(String run_code) {
        String start_station_code = stationOnLineService.getStartStation(run_code);
        String end_station_code = stationOnLineService.getEndStation(run_code);
        String start_station_name = stationService.getStationByCode(start_station_code).getName();
        String end_station_name = stationService.getStationByCode(end_station_code).getName();
        Time startTime = timeTableService.getDepartTime(run_code, start_station_code);
        Time endTime = timeTableService.getArrivalTime(run_code, end_station_code);
        return new TrainInfo(run_code, startTime, endTime, start_station_name, end_station_name);
    }
}
