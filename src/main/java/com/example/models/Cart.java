package com.example.models;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> products;
    private int quantity;
    private String username_client;



    public Cart() {
        this.quantity = 0;
        this.username_client = "";
        products = new ArrayList<>();
    }

    public Cart(int quantity, String username_client) {
        this.quantity = quantity;
        this.username_client = username_client;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setStatus(String username_client) {
        this.username_client = username_client;
    }
    public int getQuantity(){
        return this.quantity;
    }
    public String getUsername_client(){
        return this.username_client;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }
}
