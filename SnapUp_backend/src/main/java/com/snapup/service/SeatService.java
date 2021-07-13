package com.snapup.service;

import java.util.Date;

public interface SeatService {
    //创建一个列车流水的所有座位信息，在初始化（用户选择该流水）的时候完成
    public void createTrainSerialSeat(Date date, String run_code);
    //删除一个列车流水的所有座位信息，在删除一个流水之前执行
    public void deleteTrainSerialSeat(Date date, String run_code);
}
