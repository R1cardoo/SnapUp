package com.snapup.dao;

import com.snapup.pojo.Reservation;

import java.util.List;

public interface ReservationMapper {
    //创建座位预定情况
    public void createReservation(Reservation reservation);
    //查询一个列车流水上所有的座位预定情况
    public List<Reservation> findReservationBySerial(int run_serial);
    //查询一个座位的预定情况
    public boolean checkBooked(int run_serial, int coach_idx, int seat_idx);
    //更新一个座位的预定情况
    public void updateReservation(Reservation reservation);
}
