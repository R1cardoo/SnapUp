package com.example.snapup_android.service;

import com.example.snapup_android.pojo.TrainInfo;

public interface TrainRunService {
    public char getTrainType(String run_code);
    public int getStationNum(String run_code);
    public int getCoachNum(String run_code);
    public int getSeatNum(String run_code);
    public TrainInfo getTrainInfo(String run_code);
}
