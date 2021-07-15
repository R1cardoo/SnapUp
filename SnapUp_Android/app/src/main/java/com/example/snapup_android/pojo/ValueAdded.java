package com.example.snapup_android.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValueAdded {
    private String username;
    private int run_serial;
    boolean mcd;
    boolean umbrella;
    boolean insurance;

}
