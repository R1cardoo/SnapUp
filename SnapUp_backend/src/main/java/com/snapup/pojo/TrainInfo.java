package com.snapup.pojo;

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
}
