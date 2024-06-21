package com.example.models;

import java.util.List;

public class Provider {
    private String type;
    private String email;
    private String psw;
    private String username;
    private List<Store> store;

    public Provider() {
        this.type = "";
        this.email = "";
        this.psw = "";
        this.username = "";
    }

    public Provider(String type, String email, String psw, String username) {
        this.type = type;
        this.email = email;
        this.psw = psw;
        this.username = username;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPsw() {
        return psw;
    }
    public void setPsw(String psw) {
        this.psw = psw;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
