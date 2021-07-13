package com.snapup.service;

import java.util.Date;

public interface TrainSerialService {
    //在用户搜索到那天的车次之后，创建该车次的流水：
    public void createTrainSerial(Date date, String run_num);

}
