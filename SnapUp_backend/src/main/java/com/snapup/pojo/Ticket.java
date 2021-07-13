package com.snapup.pojo;

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
}
