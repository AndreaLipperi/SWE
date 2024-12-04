package com.example.models;

import jakarta.persistence.*;

@Entity
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int available_quantity;

    private double price_product;

    private String desc_prod;

    @ManyToOne
    @JoinColumn(name = "subcategory_id", nullable = false)
    private Subcategory subcategory;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private User provider;

    private int discount;

    @ManyToOne
    @JoinColumn(name = "measure_units_id", nullable = false)
    private Measure_Unit measureUnit;

    public Store() {
        this.available_quantity = 0;
        this.price_product = 0.0;
        this.desc_prod = "";
        this.discount = 0;
    }

    public Store(int available_quantity, double price_product, String desc_prod,
                 Subcategory subcategory, User provider, int discount,
                 Measure_Unit measureUnit) {
        this.available_quantity = available_quantity;
        this.price_product = price_product;
        this.desc_prod = desc_prod;
        this.subcategory = subcategory;
        this.provider = provider;
        this.discount = discount;
        this.measureUnit = measureUnit;
    }

    public Long getId() {
        return id;
    }

    public int getAvailableQuantity() {
        return available_quantity;
    }

    public void setAvailableQuantity(int available_quantity) {
        this.available_quantity = available_quantity;
    }

    public double getPriceProduct() {
        return price_product;
    }

    public void setPriceProduct(double price_product) {
        this.price_product = price_product;
    }

    public String getDescProd() {
        return desc_prod;
    }

    public void setDescProd(String desc_prod) {
        this.desc_prod = desc_prod;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public User getProvider() {
        return provider;
    }

    public void setProvider(User provider) {
        this.provider = provider;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Measure_Unit getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(Measure_Unit measureUnit) {
        this.measureUnit = measureUnit;
    }
}
