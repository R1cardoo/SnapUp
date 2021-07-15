package com.snapup.dao;

import com.snapup.pojo.TrainRun;

import java.util.List;

public interface TrainRunMapper {
    //通过车次编号查询车次信息
    public TrainRun findTrainRunByCode(String run_code);
    public List<TrainRun> findAllTrainRun();
    public void delLine(String run_code);
}
