package com.example.snapup_android.pojo;

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
    private char seat_type;
    private int run_serial;
    private int depart_station_idx;
    private int arrival_station_idx;
    private String passenger_id;
    private String username;
    private float price;

    public Order(int i, Date time, String id, int id1, int id2, char type, int serial, int idx, int idx1, String username, String id3, float price) {
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
