package com.snapup.service;

import com.snapup.pojo.TrainInfo;
import com.snapup.pojo.TrainRun;

import java.util.List;

public interface TrainRunService {
    public char getTrainType(String run_code);
    public int getStationNum(String run_code);
    public int getCoachNum(String run_code);
    public int getSeatNum(String run_code);
    public List<TrainRun> getAllTrainRun();
    public TrainInfo getTrainInfo(String run_code);
    public void delLine(String run_code);
}
