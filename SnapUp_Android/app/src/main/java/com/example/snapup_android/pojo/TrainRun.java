package com.example.snapup_android.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainRun {
    private String run_code;
    private char type;
    private int station_num;
    private int coach_num;
    private int seat_num;


}
