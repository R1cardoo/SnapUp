package com.snapup.pojo;

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
}
