package com.example.snapup_android.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Passenger {
    private String identity_id;
    private String username;
    private char identity_type;
    private String name;
    private String tele;

    public Passenger(String id, String username, char type, String name, String tele) {
        this.identity_id =id;
        this.username =username;
        this.identity_type = type;
        this.name = name;
        this.tele =tele;
    }
}
