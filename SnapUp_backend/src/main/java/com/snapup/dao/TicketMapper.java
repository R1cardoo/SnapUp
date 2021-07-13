package com.snapup.dao;

import com.snapup.pojo.SeatTicket;
import com.snapup.pojo.Ticket;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TicketMapper {
    //创建座位价格信息：
    public void createTicket(Ticket ticket);
    //查询Ticket
    public List<Ticket> findTicket(int run_serial);
    //根据列车流水，出发站和目标站查询座位类型，座位价格，余票
    public List<SeatTicket> findSeatTicket(@Param("run_serial") int run_serial, @Param("depart_station_code")String depart_station_code, @Param("arrival_station_code")String arrival_station_code);
    //根据列车流水，出发站和目标站，座位类型，更新余票量：
    public void updateTicketRemain(Ticket ticket);
}
