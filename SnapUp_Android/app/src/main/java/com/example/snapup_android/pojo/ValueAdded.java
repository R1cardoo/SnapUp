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

    public int getRun_serial() {
        return run_serial;
    }

    public String getUsername() {
        return username;
    }
    public void setUmbrella(boolean a ){
        this.umbrella = a;
    }

    public void setInsurance(boolean a ){
        this.insurance = a;
    }
    public void setMcd(boolean a ){
        this.mcd = a;
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
