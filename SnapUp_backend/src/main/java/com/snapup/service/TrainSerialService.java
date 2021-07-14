package com.snapup.service;

import com.snapup.pojo.TrainInfo;
import com.snapup.pojo.TrainRun;
import com.snapup.pojo.TrainSerial;

import java.util.Date;
import java.util.List;

public interface TrainSerialService {
    //在用户搜索到那天的车次之后，创建该车次的流水：
    public void createTrainSerial(Date date, String run_num);
    //返回所有流水列表中的train_serial:
    public List<TrainSerial> getAllTrainSerial();
    //根据流水号返回车次号、发车时间、始发站、终点站
    public TrainInfo getTrainInfo(int run_serial);
    //根据一系列车次号生成一系列流水号 //铁总接口
    public void generateTrainSerial(List<String> run_codes, int days, Date startDay);

    public List<TrainRun> getAllTrainRun();

}
