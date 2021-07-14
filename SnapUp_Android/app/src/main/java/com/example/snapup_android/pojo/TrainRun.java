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

    public char getType() {
        return type;
    }

    public int getStation_num() {
        return station_num;
    }

    public int getCoach_num() {
        return coach_num;
    }

    public int getSeat_num() {
        return seat_num;
    }

    public String getRun_code() {
        return run_code;
    }
}
