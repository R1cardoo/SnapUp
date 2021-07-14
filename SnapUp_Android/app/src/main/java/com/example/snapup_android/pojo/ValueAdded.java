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
    private boolean mcd;
    private boolean umbrella;
    private boolean insurance;

    public int getRun_serial() {
        return run_serial;
    }

    public String getUsername() {
        return username;
    }

    public boolean isMcd() {
        return mcd;
    }

    public boolean isInsurance() {
        return insurance;
    }

    public boolean isUmbrella() {
        return umbrella;
    }
}
