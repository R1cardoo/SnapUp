package com.example.snapup_android.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    public String username;
    public String identity;
    public char gender;
    public String name;
    public String number;
    public String mail;
    public String password;
    public String nickname;

    public User(){}
    public User(String username, String id, char gender, String name, String number, String mail, String password, String nickname) {
        this.username = username;
        this.gender =gender;
        this.name =name;
        this.number =number;
        this.mail =mail;
        this.password =password;
        this.nickname =nickname;
        this.identity =id;
    }

    public String getPassword() {
        return password;
    }

    public String getIdentity() {
        return identity;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
