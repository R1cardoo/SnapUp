package com.example.snapup_android.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    private int run_serial;
    private String depart_station_code;
    private String arrival_station_code;
    private char seat_type;
    private float seat_price;
    private int remain;

    public Ticket(int serial, String code, String code1, char c, float price1, int i) {
        this.seat_price = price1;
        this.run_serial =serial;
        this.arrival_station_code =code1;
        this.depart_station_code = code;
        this.seat_type = c;
        this.remain =i;
    }
}
