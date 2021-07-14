package com.snapup.dao;

import com.snapup.pojo.TrainSerial;

import java.util.Date;
import java.util.List;

public interface TrainSerialMapper {
    //创建行车班次流水:
    public void createTrainSerial(TrainSerial trainSerial);
    //查询行车班次流水：
    public TrainSerial findTrainSerial(TrainSerial trainSerial);
    //根据行车流水号查询流水：
    public TrainSerial findTrainSerialBySerialNum(int run_serial);
    //根据流水号删除流水信息 TODO:改
    public int deleteTrainSerial(int serial);
    //根据时间查询一系列行车班次流水信息：
    public List<TrainSerial> findTrainSerialByTime(Date date);
    //根据车次查询一系列行车班次流水信息：
    public List<TrainSerial> findTrainSerialByTrainRun(String run_code);
    //返回所有的行车班次流水：
    public List<TrainSerial> findAllTrainSerial();
    //返回所有的车次：
    public List<String> getAllTrainRunCode();
}
