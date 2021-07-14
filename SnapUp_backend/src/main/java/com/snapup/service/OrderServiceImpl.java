package com.snapup.service;

import com.snapup.dao.*;
import com.snapup.pojo.Order;
import com.snapup.pojo.SeatTicket;
import com.snapup.pojo.TrainRun;
import com.snapup.pojo.ValueAdded;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService{
    private OrderMapper orderMapper;
    private TrainRunMapper trainRunMapper;
    private StationService stationService;
    private TrainSerialMapper trainSerialMapper;
    private StationOnLineMapper stationOnLineMapper;
    private TicketMapper ticketMapper;
    private TicketService ticketService;
    private TrainRunService trainRunService;

    public void setTrainRunService(TrainRunService trainRunService) {
        this.trainRunService = trainRunService;
    }

    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public void setTicketMapper(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }

    public void setStationOnLineMapper(StationOnLineMapper stationOnLineMapper) {
        this.stationOnLineMapper = stationOnLineMapper;
    }

    public void setTrainSerialMapper(TrainSerialMapper trainSerialMapper) {
        this.trainSerialMapper = trainSerialMapper;
    }

    public void setStationService(StationService stationService) {
        this.stationService = stationService;
    }

    public void setTrainRunMapper(TrainRunMapper trainRunMapper) {
        this.trainRunMapper = trainRunMapper;
    }

    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public int createOrderLow(int run_serial, int coach_id, int seat_id, int depart_station_idx, int arrival_station_idx, String username, String passenger_id, float price, char seat_type) {

        if(orderMapper.findOrder(run_serial, username, passenger_id) != null){
            return 1;
        }
        Date currentTime = new Date();
        String ticketID = ""+run_serial+"-"+coach_id+"-"+seat_id+"-"+depart_station_idx+"->"+arrival_station_idx;
        orderMapper.createOrder(new Order(0, currentTime, ticketID, coach_id, seat_id, seat_type, run_serial, depart_station_idx, arrival_station_idx, username, passenger_id, price));
        return 0;
    }

    public Order findOrder(int run_serial, String username, String passenger_id) {
        return orderMapper.findOrder(run_serial, username, passenger_id);
    }

    public int deleteOrder(int run_serial, String username, String passenger_id) {
        return orderMapper.deleteOrder(run_serial, username, passenger_id);
    }

    public List<Order> findAllUserOrder(String username) {
        return orderMapper.findOrderByUsername(username);
    }

    //创建订单顶层封装
    public float createOrder(int run_serial, String depart_station_name, String arrival_station_name, char seat_type, String username) {
        TrainRun trainRun = trainRunMapper.findTrainRunByCode(trainSerialMapper.findTrainSerialBySerialNum(run_serial).getRun_code());
        String depart_station_code = stationService.getStationByName(depart_station_name).getCode();
        String arrival_station_code = stationService.getStationByName(arrival_station_name).getCode();
        int depart_station_idx = stationOnLineMapper.findStationIdx(trainRun.getRun_code(), depart_station_code);
        int arrival_station_idx = stationOnLineMapper.findStationIdx(trainRun.getRun_code(), arrival_station_code);
        //自动生成车厢号和座位号

        int coach_id = 0 ;
        int seat_id = 0;

        //TODO: PassengerID 可以不是用户本身
        String passenger_id = username;
        float price = 0;
        List<SeatTicket> seatTickets = ticketMapper.findSeatTicket(run_serial, depart_station_code, arrival_station_code);
        for(SeatTicket seatTicket:seatTickets){
            if(seatTicket.getSeat_type() == seat_type){
                price = seatTicket.getSeat_price();
            }
        }
        int isOKBuyTicket = 0;
        int isOKCreateOrder = createOrderLow(run_serial, coach_id, seat_id, depart_station_idx, arrival_station_idx, username, passenger_id, price, seat_type);
        if(isOKCreateOrder == 0){
            ticketService.buyTicket(run_serial, depart_station_code, arrival_station_code, seat_type);
        }
        if((isOKCreateOrder + isOKBuyTicket) == 0){
            return price;
        }
        return 0;
    }

    public List<Integer> generateCoachAndSeat(int run_serial, char seat_type) {
        List<Integer> CoachAndSeat = new ArrayList<Integer>();
        String run_code = trainSerialMapper.findTrainSerialBySerialNum(run_serial).getRun_code();
        char train_type = trainRunService.getTrainType(run_code);
        int coach_num = trainRunService.getCoachNum(run_code);
        int seat_num = trainRunService.getSeatNum(run_code);
        if(train_type == 'D'){

        }
        else{ //'G'

        }
        return null;
    }

    public boolean checkValid(String username, int run_serial) {
        if(orderMapper.findOrder(run_serial, username, username) == null){
            return false;
        }
        return true;
    }
    public void updateOrder(ValueAdded valueAdded) {
        Order order = orderMapper.findOrder(valueAdded.getRun_serial(), valueAdded.getUsername(), valueAdded.getUsername());
        float price = order.getPrice();
        if(valueAdded.isMcd()){
            price += 30.0;
        }
        if(valueAdded.isInsurance()){
            price += 30;
        }
        if(valueAdded.isUmbrella()){
            price += 20;
        }
        order.setPrice(price);
        orderMapper.updateOrder(order);
    }
}
