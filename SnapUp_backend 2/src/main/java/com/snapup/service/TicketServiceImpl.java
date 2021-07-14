package com.snapup.service;

import com.snapup.dao.*;
import com.snapup.pojo.*;

import java.util.List;

public class TicketServiceImpl implements TicketService {
    private TicketMapper ticketMapper;
    private StationMapper stationMapper;
    private TrainSerialMapper trainSerialMapper;
    private TrainRunMapper trainRunMapper;
    private SeatMapper seatMapper;
    private StationOnLineMapper stationOnLineMapper;

    public void setStationOnLineMapper(StationOnLineMapper stationOnLineMapper) {
        this.stationOnLineMapper = stationOnLineMapper;
    }

    private StationOnLineService stationOnLineService;

    public void setStationOnLineService(StationOnLineService stationOnLineService) {
        this.stationOnLineService = stationOnLineService;
    }

    public void setSeatMapper(SeatMapper seatMapper) {
        this.seatMapper = seatMapper;
    }

    public void setTrainSerialMapper(TrainSerialMapper trainSerialMapper) {
        this.trainSerialMapper = trainSerialMapper;
    }

    public void setTicketMapper(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }

    public void setStationMapper(StationMapper stationMapper) {
        this.stationMapper = stationMapper;
    }

    public void setTrainRunMapper(TrainRunMapper trainRunMapper) {
        this.trainRunMapper = trainRunMapper;
    }

    public int initial(int run_serial) {
        //查询，如果有了就不需要初始化了
        if(ticketMapper.findTicket(run_serial) == null){
            return 1;
        }
        TrainRun trainRun = trainRunMapper.findTrainRunByCode(trainSerialMapper.findTrainSerialBySerialNum(run_serial).getRun_code());
        List<Station> stations = stationOnLineService.getAllStation(trainRun.getRun_code());
        for(int i=0;i<stations.size()-1;i++){
            for(int j=i+1;j<stations.size();j++){
                createTicket(run_serial, stations.get(i).getCode(), stations.get(j).getCode());
            }
        }
        return 0;
    }

    public void createTicket(int run_serial, String depart_station_code, String arrival_station_code) {
        //System.out.println(ticketMapper.findSeatTicket(run_serial, depart_station_code, arrival_station_code).isEmpty());
        if (ticketMapper.findSeatTicket(run_serial, depart_station_code, arrival_station_code).isEmpty()) {
            String[] coordinate1 = stationMapper.findStationByCode(depart_station_code).getAddress().split(" ");
            String[] coordinate2 = stationMapper.findStationByCode(arrival_station_code).getAddress().split(" ");
            TrainRun trainRun = trainRunMapper.findTrainRunByCode(trainSerialMapper.findTrainSerialBySerialNum(run_serial).getRun_code());
            int seat_num = trainRun.getSeat_num();
            char train_type = trainRun.getType();

            float price1 = tickets_price_produce(train_type, '1', Float.parseFloat(coordinate1[0]),
                    Float.parseFloat(coordinate1[1]), Float.parseFloat(coordinate2[0]),
                    Float.parseFloat(coordinate2[1]), seat_num);
            float price2 = tickets_price_produce(train_type, '2', Float.parseFloat(coordinate1[0]),
                    Float.parseFloat(coordinate1[1]), Float.parseFloat(coordinate2[0]),
                    Float.parseFloat(coordinate2[1]), seat_num);
            if (train_type == 'D') {
                ticketMapper.createTicket(new Ticket(run_serial, depart_station_code, arrival_station_code, '1', price1, 60));
                ticketMapper.createTicket(new Ticket(run_serial, depart_station_code, arrival_station_code, '2', price2, 480));
            } else { //高铁
                ticketMapper.createTicket(new Ticket(run_serial, depart_station_code, arrival_station_code, '1', price1, 100));
                ticketMapper.createTicket(new Ticket(run_serial, depart_station_code, arrival_station_code, '2', price2, 600));
            }

        }
    }
    //票数减一
    public int subTicket(int run_serial, String depart_station_code, String arrival_station_code, char seat_type) {
        String[] coordinate1 = stationMapper.findStationByCode(depart_station_code).getAddress().split(" ");
        String[] coordinate2 = stationMapper.findStationByCode(arrival_station_code).getAddress().split(" ");
        List<SeatTicket> ticketList = ticketMapper.findSeatTicket(run_serial, depart_station_code, arrival_station_code);
        TrainRun trainRun = trainRunMapper.findTrainRunByCode(trainSerialMapper.findTrainSerialBySerialNum(run_serial).getRun_code());
        char train_type = trainRun.getType();
        int remain = 0;
        for(SeatTicket seatTicket : ticketList){
            if(seatTicket.getSeat_type() == seat_type){
                remain = seatTicket.getRemain();
            }
        }
        if(remain == 0){
            return 1;
        }
        remain --;
        float price = tickets_price_produce(train_type, seat_type, Float.parseFloat(coordinate1[0]),
                Float.parseFloat(coordinate1[1]), Float.parseFloat(coordinate2[0]),
                Float.parseFloat(coordinate2[1]), remain);

        ticketMapper.updateTicketRemain(new Ticket(run_serial, depart_station_code, arrival_station_code, seat_type, price, remain));
        return 0;
    }
    //票数加一
    public int addTicket(int run_serial, String depart_station_code, String arrival_station_code, char seat_type) {
        String[] coordinate1 = stationMapper.findStationByCode(depart_station_code).getAddress().split(" ");
        String[] coordinate2 = stationMapper.findStationByCode(arrival_station_code).getAddress().split(" ");
        List<SeatTicket> ticketList = ticketMapper.findSeatTicket(run_serial, depart_station_code, arrival_station_code);
        TrainRun trainRun = trainRunMapper.findTrainRunByCode(trainSerialMapper.findTrainSerialBySerialNum(run_serial).getRun_code());
        char train_type = trainRun.getType();
        int remain = 0;
        for(SeatTicket seatTicket : ticketList){
            if(seatTicket.getSeat_type() == seat_type){
                remain = seatTicket.getRemain();
            }
        }
        int max_seat_num = seatMapper.findSeatNum(trainRun.getRun_code(), seat_type);
        //System.out.println(max_seat_num);
        if(remain == max_seat_num)
            return 1;
        remain++;
        float price = tickets_price_produce(train_type, seat_type, Float.parseFloat(coordinate1[0]),
                Float.parseFloat(coordinate1[1]), Float.parseFloat(coordinate2[0]),
                Float.parseFloat(coordinate2[1]), remain);
        ticketMapper.updateTicketRemain(new Ticket(run_serial, depart_station_code, arrival_station_code, seat_type, price, remain));
        return 0;
    }

    public int buyTicket(int run_serial, String depart_station_code, String arrival_station_code, char seat_type) {
        TrainRun trainRun = trainRunMapper.findTrainRunByCode(trainSerialMapper.findTrainSerialBySerialNum(run_serial).getRun_code());
        List<Station> stations = stationOnLineService.getAllStation(trainRun.getRun_code());
        int depart_station_idx = stationOnLineMapper.findStationIdx(trainRun.getRun_code(), depart_station_code);
        int arrival_station_idx = stationOnLineMapper.findStationIdx(trainRun.getRun_code(), arrival_station_code);
        for(int i=0;i<stations.size()-1;i++){
            for(int j=i+1;j<stations.size();j++){
                //System.out.println(stations.get(i).toString()+"->"+stations.get(j));
                int idx1 = stationOnLineMapper.findStationIdx(trainRun.getRun_code(), stations.get(i).getCode());
                int idx2 = stationOnLineMapper.findStationIdx(trainRun.getRun_code(), stations.get(j).getCode());
                //只有两站同时在departStation和arrivalStation的一侧，不需要减票
                if((idx1<=depart_station_idx && idx2<=depart_station_idx) || (idx1>=arrival_station_idx && idx2>=arrival_station_idx));
                else{
                    subTicket(run_serial, stations.get(i).getCode(), stations.get(j).getCode(), seat_type);
                }
            }
        }
        return 0;
    }

    public int returnTicket(int run_serial, String depart_station_code, String arrival_station_code, char seat_type) {
        TrainRun trainRun = trainRunMapper.findTrainRunByCode(trainSerialMapper.findTrainSerialBySerialNum(run_serial).getRun_code());
        List<Station> stations = stationOnLineService.getAllStation(trainRun.getRun_code());
        int depart_station_idx = stationOnLineMapper.findStationIdx(trainRun.getRun_code(), depart_station_code);
        int arrival_station_idx = stationOnLineMapper.findStationIdx(trainRun.getRun_code(), arrival_station_code);
        for(int i=0;i<stations.size()-1;i++){
            for(int j=i+1;j<stations.size();j++){
                //System.out.println(stations.get(i).toString()+"->"+stations.get(j));
                int idx1 = stationOnLineMapper.findStationIdx(trainRun.getRun_code(), stations.get(i).getCode());
                int idx2 = stationOnLineMapper.findStationIdx(trainRun.getRun_code(), stations.get(j).getCode());
                //只有两站同时在departStation和arrivalStation的一侧，不需要加票
                if((idx1<=depart_station_idx && idx2<=depart_station_idx) || (idx1>=arrival_station_idx && idx2>=arrival_station_idx));
                else{
                    addTicket(run_serial, stations.get(i).getCode(), stations.get(j).getCode(), seat_type);
                }
            }
        }
        return 0;
    }

    public List<SeatTicket> findSeatTicket(int run_serial, String depart_station_code, String arrival_station_code) {
        return ticketMapper.findSeatTicket(run_serial, depart_station_code, arrival_station_code);
    }
    //更新票价：在初始化的时候、购票者购票or退票的时候

    /*      train_type  (G->高铁，D->动车）
     *      seat_type   (1->座1，2->座2)
     *      station_num (乘站数量)
     *      remain      (剩余票量，高铁0~800，动车0~540)
     *
     *      本函数目的：生成票价
     *      算法：票价 = 列车类型 * 座位类型 * 距离 * (1- 距离衰减率) * 剩余票量区间
     *      输入：列车类型，座位类型，点1经度，点1纬度，点2经度，点2纬度，余票量
     *      输出：票价  (约定：当返回值为-1时，表示输入数据不合法)
     */
    public float tickets_price_produce(char train_type, char seat_type, float latitude_x, float longitude_x,
                                       float latitude_y, float longitude_y, int remain) {
        float price = 0;  /* 返回的票价 */
        float distance = (float) from_loc_cal_distance(latitude_x, longitude_x, latitude_y, longitude_y); /* 计算距离 */
        float price_per_km = (float) 0.28;   /* 火车每千米单价 */
        float G_ratio = (float) 0.05, D_ratio = (float) 0.03;     /* 随余票减少，票价升高比率 */
        float Seat_1_ratio = (float) 1.50, Seat_2_ratio = (float) 1.10; /* 一等座，二等座价格较基础比率 */
        float distance_attenuation_ratio = 0;       /* 随距离的价格衰减率 */
        float G_price_ratio = (float) 1.2, D_price_ratio = (float) 1.0;      /* 高铁、火车溢价率 */
        int G_tickets_num_max = 800, D_tickets_num_max = 540;       /* 高铁、动车剩余票数最大值*/
        int Interval_between = 100;     /* 区间间隔，也就是余量每减多少，加价 */
        int Interval = 0;   /* 余票量在哪个区间 */

        /* 根据距离计算价格衰减率 */
        if (distance <= 200) distance_attenuation_ratio = 0;
        else if (distance <= 500) distance_attenuation_ratio = (float) 0.1;
        else if (distance <= 1000) distance_attenuation_ratio = (float) 0.2;
        else if (distance <= 1500) distance_attenuation_ratio = (float) 0.3;
        else if (distance <= 2500) distance_attenuation_ratio = (float) 0.4;
        else distance_attenuation_ratio = (float) 0.5;

        price = distance * price_per_km * (1 - distance_attenuation_ratio);

        /* 列车类型 * 乘坐站数 * 剩余票量区间 */
        switch (train_type) {
            case 'G':
                Interval = (G_tickets_num_max - remain) / Interval_between;
                price *= G_price_ratio * (1 + G_ratio * Interval);
                break;
            case 'D':
                Interval = (D_tickets_num_max - remain) / Interval_between;
                price *= D_price_ratio * (1 + D_ratio * Interval);
                break;
            default:
                return -1;   /* 表示不合法的数据输入 */
        }

        /* 座位类型 */
        switch (seat_type) {
            case '1':
                price *= Seat_1_ratio;
                break;
            case '2':
                price *= Seat_2_ratio;
                break;
            default:
                price = -1; /* 表示不合法的数据输入 */
        }

        return price;
    }

    /*      本函数的目的是：计算弧度
     *      输入：角度
     *      输出：弧度
     */
    public double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /*      本函数的目的是：根据经纬度计算两点间的距离
     *      公式：d = 2arcsin(sqrt(sin(a/s)^2+cos(lat1)*cos(lat2)*sin(b/2)^2))*R
     *      输入：两地的经纬度
     *      输出：两地间的距离
     */
    public double from_loc_cal_distance(float latitude_x, float longitude_x, float latitude_y, float longitude_y) {
        double radLat1 = rad(latitude_x);
        double radLat2 = rad(latitude_y);
        double a = radLat1 - radLat2;
        double b = rad(longitude_x) - rad(longitude_y);
        double d = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        d *= EARTH_RADIUS;
        return d;
    }
}

