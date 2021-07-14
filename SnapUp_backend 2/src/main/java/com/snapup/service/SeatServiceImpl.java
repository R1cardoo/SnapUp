package com.snapup.service;

import com.snapup.dao.SeatMapper;
import com.snapup.pojo.Seat;

import java.util.Date;

public class SeatServiceImpl implements SeatService{
    private SeatMapper seatMapper;

    private TrainRunService trainRunService;

    public void setSeatMapper(SeatMapper seatMapper) {
        this.seatMapper = seatMapper;
    }

    public void setTrainRunService(TrainRunService trainRunService) {
        this.trainRunService = trainRunService;
    }

    public void createTrainSerialSeat(Date date, String run_code) {
        int seat_num = trainRunService.getSeatNum(run_code);
        int coach_num = trainRunService.getCoachNum(run_code);
        int train_type = trainRunService.getTrainType(run_code);

        if(train_type == 'D')
            for(int i=0;i<coach_num;i++){
                //两节一等座（30人/节）
                if(i == 0 || i == 1)
                    for(int j=0;j<30;j++){
                        seatMapper.createSeat(new Seat(run_code, i+1, j+1, '1'));
                    }
                //剩下全是二等座
                else
                    for(int j=0;j<(seat_num-2*30)/(coach_num-2);j++){
                        seatMapper.createSeat(new Seat(run_code, i+1, j+1, '2'));
                }
            }
        else
            for(int i=0;i<coach_num;i++){
                //两节一等座（50人/节）
                if(i == 0 || i == 1)
                    for(int j=0;j<50;j++){
                        seatMapper.createSeat(new Seat(run_code, i+1, j+1, '1'));
                    }
                //剩下全是二等座
                else
                    for(int j=0;j<(seat_num-2*50)/(coach_num-2);j++){
                        seatMapper.createSeat(new Seat(run_code, i+1, j+1, '2'));
                    }
            }
    }

    public void deleteTrainSerialSeat(Date date, String run_code) {

    }

}
