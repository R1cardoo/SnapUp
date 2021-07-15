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

}
