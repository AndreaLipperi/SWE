package com.example.models;

import java.util.List;

public class Client extends User {
    private List<Cart> cart;
    private List<Order> orders;

    public Client() {
        super();
    }

    public Client(String type, String email, String psw, String username, List<Cart> cart, List<Order> orders) {
        super(type, email, psw, username);
        this.cart = cart;
        this.orders = orders;
    }

    public List<Cart> getCart() {
        return cart;
    }
    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
