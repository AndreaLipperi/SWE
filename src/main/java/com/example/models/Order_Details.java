package com.example.models;

import java.util.ArrayList;
import java.util.List;

public class Order_Details {
    private List<Product> products;
    private int quantity;
    private String status;



    public Order_Details() {
        this.quantity = 0;
        this.status = "";
        products = new ArrayList<>();
    }

    public Order_Details(int quantity, String status) {
        this.quantity = quantity;
        this.status = status;
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

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }
}
