package com.snapup.service;

public interface TicketService {
    double EARTH_RADIUS = 6378.137; //地球半径(单位：千米)
    //真正的初始化：
    public int initial(int run_serial);
    //创建座位价格信息：
    public void createTicket(int run_serial, String depart_station_code,
                                String arrival_station_code);
    //票量加一：
    public int addTicket(int run_serial, String depart_station_code,
                                String arrival_station_code, char seat_type);
    //票量减一：
    public int subTicket(int run_serial, String depart_station_code,
                            String arrival_station_code, char seat_type);
    //购票：
    public int buyTicket(int run_serial, String depart_station_code,
                         String arrival_station_code, char seat_type);
    //退票：
    public int returnTicket(int run_serial, String depart_station_code,
                         String arrival_station_code, char seat_type);

    //计算票价：
    public float tickets_price_produce(char train_type, char seat_type,float latitude_x,
                                       float longitude_x, float latitude_y, float longitude_y,
                                       int remain);
    //距离计算函数
    public double from_loc_cal_distance(float latitude_x, float longitude_x,
                                        float latitude_y, float longitude_y);
    //弧度计算函数
    public double rad(double d);

}
