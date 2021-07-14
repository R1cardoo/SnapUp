package com.example.snapup_android.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeTable {
    private String num_code;
    private String station_code;
    private Time departure_time;
    private Time arrival_time;

    public Time getArrival_time() {
        return arrival_time;
    }

    public Time getDeparture_time() {
        return departure_time;
    }
}
