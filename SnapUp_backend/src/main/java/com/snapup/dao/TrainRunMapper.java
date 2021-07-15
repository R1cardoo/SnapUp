package com.snapup.dao;

import com.snapup.pojo.TrainRun;
import org.apache.ibatis.annotations.Param;

import java.sql.Time;
import java.util.List;

public interface TrainRunMapper {
    //通过车次编号查询车次信息
    public TrainRun findTrainRunByCode(String run_code);
    public List<TrainRun> findAllTrainRun();
    public void delLine(String run_code);
    public void createTrainRun(@Param("run_code") String run_code, @Param("'type'") char type,
                                @Param("station_num") int station_num, @Param("coach_num") int coach_num,
                               @Param("seat_num") int seat_num);
}
