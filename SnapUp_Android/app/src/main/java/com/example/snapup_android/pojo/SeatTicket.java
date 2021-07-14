package com.example.snapup_android.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatTicket {
    private char seat_type;
    private float seat_price;
    private int remain;

    public char getSeat_type() {
        return seat_type;
    }

    public float getSeat_price() {
        return seat_price;
    }

    public int getRemain() {
        return remain;
    }
}
