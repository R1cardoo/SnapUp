package com.snapup.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TrainPOJO {
    private String run_code;
    private char type;
    private int station_num;
    private String depart_name;
    private String arrive_name;
    private Date depart_time;
    private Date arrive_time;
}
