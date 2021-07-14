package com.example.snapup_android.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestrictedUsr {
    String name;
    String identity_id;

    public String getIdentity_id() {
        return identity_id;
    }
}
