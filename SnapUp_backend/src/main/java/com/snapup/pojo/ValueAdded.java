package com.snapup.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValueAdded {
    private String username;
    private int run_serial;
    private boolean mcd;
    private boolean umbrella;
    private boolean insurance;
}
