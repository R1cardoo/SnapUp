package com.example.snapup_android.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    private String num_code;
    private int coach_idx;
    private int seat_idx;
    private char seat_type;

    public Seat(String code, int i, int i1, char c) {
        num_code = code;
        coach_idx = i;
        seat_idx = i1;
        seat_type = c;
    }
}
