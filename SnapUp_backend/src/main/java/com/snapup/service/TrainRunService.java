package com.snapup.service;

public interface TrainRunService {
    public char getTrainType(String run_code);
    public int getStationNum(String run_code);
    public int getCoachNum(String run_code);
    public int getSeatNum(String run_code);

}
