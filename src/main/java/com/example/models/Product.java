package com.example.models;

public class Product {
    private String username_prov;
    private String description;
    private double price;
    private String subcategory;
    private int q_available;

    public Product() {
        this.username_prov = "";
        this.description = "";
        this.price = 0.00;
        this.subcategory = "";
        this.q_available = 0;
    }
    public Product(String username_prov, String description, double price, String subcategory, int q_available) {
        this.username_prov = username_prov;
        this.description = description;
        this.price = price;
        this.subcategory = subcategory;
        this.q_available = q_available;
    }

    public String getUsername_prov() {
        return username_prov;
    }
    public void setUsername_prov(String username_prov) {
        this.username_prov = username_prov;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getSubcategory() {
        return subcategory;
    }
    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }
    public int getQ_available() {
        return q_available;
    }
    public void setQ_available(int q_available) {
        this.q_available = q_available;
    }
}
