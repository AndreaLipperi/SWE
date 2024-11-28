package com.example.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders_details")
public class Order_Details {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private int quantity;
    private String status;


    public Order_Details() {
        this.quantity = 0;
        this.status = "";
    }

    public Order_Details(Store store, Order order, int quantity, String status) {
        this.store = store;
        this.order = order;
        this.quantity = quantity;
        this.status = status;
    }
    public Long getId() {
        return id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getQuantity(){
        return this.quantity;
    }
    public String getStatus(){
        return this.status;
    }
    public Store getStore() {
        return store;
    }
    public void setStore(Store store) {
        this.store = store;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }

    public double getTotalPrice() {
        return store.getPriceProduct() * quantity;
    }
    public double getPriceDiscounted() {
        return (store.getPriceProduct() - (store.getPriceProduct() * store.getDiscount() / 100)) * quantity;
    }
}
