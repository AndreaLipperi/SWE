package com.example.models;

import java.util.List;

public class Provider extends User {
    private List<Store> store;

    public Provider() {
        super();
    }

    public Provider(String type, String email, String psw, String username, List<Store> store) {
        super(type, email, psw, username);
        this.store = store;
    }

    public List<Store> getStore() {
        return store;
    }
    public void setStore(List<Store> store) {
        this.store = store;
    }
}
