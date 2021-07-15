package com.example.snapup_android.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainInfo {
    public String num_code;
    public Time startTime;
    public Time endTime;
    public String depart_station_name;
    public String arrival_station_name;


}
