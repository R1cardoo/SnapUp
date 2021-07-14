package com.snapup.service;

import com.snapup.dao.TrainRunMapper;
import com.snapup.pojo.TrainRun;

import java.util.List;

public class TrainRunServiceImpl implements TrainRunService{
    private TrainRunMapper trainRunMapper;

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
    public List<TrainRun> getAllTrainRun(){
        return trainRunMapper.findAllTrainRun();
    }
}
