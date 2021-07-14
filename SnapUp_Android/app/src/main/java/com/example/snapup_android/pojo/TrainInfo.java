package com.example.snapup_android.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainInfo {
    private String num_code;
    private Time startTime;
    private Time endTime;
    private String depart_station_name;
    private String arrival_station_name;

    public TrainInfo(){}
    public TrainInfo(String code, Time time, Time time1, String name, String name1) {
        this.num_code =code;
        this.startTime = time;
        this.endTime = time1;
        this.depart_station_name = name;
        this.arrival_station_name = name1;
    }
}
