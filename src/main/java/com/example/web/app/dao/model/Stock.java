package com.example.web.app.dao.model;

public class Stock {
    private int id;
    private String location;
    private int quantity;
    private String name;
    private String who_sell;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWho_sell() {
        return who_sell;
    }

    public void setWho_sell(String who_sell) {
        this.who_sell = who_sell;
    }
}
