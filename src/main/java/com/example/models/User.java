package com.example.models;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String email;
    private String password;
    private String username;

    public User() {
        this.type = "";
        this.email = "";
        this.password = "";
        this.username = "";
    }

    public User(String type, String email, String psw, String username) {
        this.type = type;
        this.email = email;
        this.password = psw;
        this.username = username;
    }
    public Long getId() {
        return id;
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
        return password;
    }
    public void setPsw(String psw) {
        this.password = psw;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
