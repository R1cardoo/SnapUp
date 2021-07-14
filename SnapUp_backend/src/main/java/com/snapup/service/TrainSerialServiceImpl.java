package com.snapup.service;



import com.snapup.dao.TrainRunMapper;
import com.snapup.dao.TrainSerialMapper;
import com.snapup.pojo.TrainInfo;
import com.snapup.pojo.TrainRun;
import com.snapup.pojo.TrainSerial;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TrainSerialServiceImpl implements TrainSerialService{
    private TrainSerialMapper trainSerialMapper;
    private StationOnLineService stationOnLineService;
    private StationService stationService;
    private TimeTableService timeTableService;
    private TrainRunMapper trainRunMapper;

    public void setTrainRunMapper(TrainRunMapper trainRunMapper) {
        this.trainRunMapper = trainRunMapper;
    }

    public void setTimeTableService(TimeTableService timeTableService) {
        this.timeTableService = timeTableService;
    }

    public void setStationService(StationService stationService) {
        this.stationService = stationService;
    }

    public void setStationOnLineService(StationOnLineService stationOnLineService) {
        this.stationOnLineService = stationOnLineService;
    }

    public void setTrainSerialMapper(TrainSerialMapper trainSerialMapper) {
        this.trainSerialMapper = trainSerialMapper;
    }

    public void createTrainSerial(Date date, String run_num) {
        if(trainSerialMapper.findTrainSerial(new TrainSerial(0, date, run_num)) == null){
            trainSerialMapper.createTrainSerial(new TrainSerial(0, date, run_num));
        }
    }

    public List<TrainSerial> getAllTrainSerial() {
        return trainSerialMapper.findAllTrainSerial();
    }

    public TrainInfo getTrainInfo(int run_serial) {
        TrainSerial trainSerial = trainSerialMapper.findTrainSerialBySerialNum(run_serial);
        String run_code = trainSerial.getRun_code();
        String start_station_code = stationOnLineService.getStartStation(run_code);
        String end_station_code = stationOnLineService.getEndStation(run_code);
        String start_station_name = stationService.getStationByCode(start_station_code).getName();
        String end_station_name = stationService.getStationByCode(end_station_code).getName();
        Time startTime = timeTableService.getDepartTime(run_code, start_station_code);
        Time endTime = timeTableService.getArrivalTime(run_code, end_station_code);
        return new TrainInfo(run_code, startTime, endTime, start_station_name, end_station_name);
    }

    public void generateTrainSerial(List<String> run_codes, int days, Date startDay) {
        for(String run_code: run_codes){
            Date day = startDay;
            for(int i=0;i<days;i++){
                createTrainSerial(day, run_code);
                Calendar c = Calendar.getInstance();
                c.setTime(day);
                c.add(Calendar.DAY_OF_MONTH, 1);
                day = c.getTime();
            }
        }
    }
    public List<TrainRun> getAllTrainRun(){
        List<String> run_codes = trainSerialMapper.getAllTrainRunCode();
        List<TrainRun> trainRuns = new ArrayList<TrainRun>();
        for(String run_code:run_codes){
            trainRuns.add(trainRunMapper.findTrainRunByCode(run_code));
        }
        return trainRuns;
    }
}
