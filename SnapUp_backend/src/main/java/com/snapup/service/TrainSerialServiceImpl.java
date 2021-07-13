package com.snapup.service;



import com.snapup.dao.TrainSerialMapper;
import com.snapup.pojo.TrainSerial;

import java.util.Date;

public class TrainSerialServiceImpl implements TrainSerialService{
    private TrainSerialMapper trainSerialMapper;

    public void setTrainSerialMapper(TrainSerialMapper trainSerialMapper) {
        this.trainSerialMapper = trainSerialMapper;
    }

    public void createTrainSerial(Date date, String run_num) {
        if(trainSerialMapper.findTrainSerial(new TrainSerial(0, date, run_num)) == null){
            trainSerialMapper.createTrainSerial(new TrainSerial(0, date, run_num));
        }
    }
}
