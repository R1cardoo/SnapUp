package com.example.snapup_android.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainSerial {
    private int serial;
    private Date date;
    private String run_code;

    public TrainSerial(int i, Date date, String num) {
        this.serial =i;
        this.date =date;
        this.run_code = num;
    }

    public String getRun_code() {
        return run_code;
    }
}
