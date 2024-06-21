package com.example.models;

import java.time.LocalDate;

public class Order {
    private LocalDate date;
    private String status;
    private String username_client;
    private Order_Details orderDetails;

    public Order(String username_client) {
        this.date = LocalDate.now();
        this.status = "Pending";
        this.username_client = username_client;
        this.orderDetails = new Order_Details();
    }

    public LocalDate getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username_client;
    }

    public Order_Details getOrderDetails() {
        return orderDetails;
    }

}
