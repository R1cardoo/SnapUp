package com.snapup.dao;

import com.snapup.pojo.TrainRun;

public interface TrainRunMapper {
    //通过车次编号查询车次信息
    public TrainRun findTrainRunByCode(String run_code);
}
