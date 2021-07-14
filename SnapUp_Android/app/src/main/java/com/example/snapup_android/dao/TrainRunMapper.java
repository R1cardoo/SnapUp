package com.example.snapup_android.dao;

import com.example.snapup_android.pojo.TrainRun;

public interface TrainRunMapper {
    //通过车次编号查询车次信息
    public TrainRun findTrainRunByCode(String run_code);
}
