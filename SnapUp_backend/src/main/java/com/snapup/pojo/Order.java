package com.snapup.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int order_id;
    private Date purchase_time;
    private String ticket_id;
    private int coach_id;
    private int seat_id;
    private int run_serial;
    private int depart_station_idx;
    private int arrival_station_idx;
    private String passenger_id;
    private String username;
    private float price;

}
