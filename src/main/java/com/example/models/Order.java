package com.example.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date_order;
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Order() {
        this.status = "";
    }

    public Order(Long id, Date date_order, String status, User user) {
        this.id = id;
        this.date_order = date_order;
        this.status = status;
        this.user = user;
    }
    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date_order;
    }

    public void setDate(Date date_order) {
        this.date_order = date_order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
