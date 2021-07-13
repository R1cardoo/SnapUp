package com.snapup.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String username;
    private String identity_id;
    private char gender;
    private String name;
    private String tele;
    private String mail;
    private String pwd;
    private String nickname;
}
