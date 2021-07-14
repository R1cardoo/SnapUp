package com.example.snapup_android.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Station_on_line {
    private String run_code;
    private int station_idx;
    private String station_code;

    public String getRun_code() {
        return run_code;
    }

    public int getStation_idx() {
        return station_idx;
    }

    public Object getStation_code() {
        return station_code;
    }
}
