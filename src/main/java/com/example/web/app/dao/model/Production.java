package com.example.web.app.dao.model;

import java.util.Date;

public class Production {
    private int id;
    private int stock_id;
    private int modelhoody_id;
    private int workshop_id;
    private int quantity_supply;
    private Date date_production;
    private String reviem;

    public String getReviem() {
        return reviem;
    }

    public void setReviem(String reviem) {
        this.reviem = reviem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock_id() {
        return stock_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }

    public int getModelhoody_id() {
        return modelhoody_id;
    }

    public void setModelhoody_id(int modelhoody_id) {
        this.modelhoody_id = modelhoody_id;
    }

    public int getWorkshop_id() {
        return workshop_id;
    }

    public void setWorkshop_id(int workshop_id) {
        this.workshop_id = workshop_id;
    }

    public int getQuantity_supply() {
        return quantity_supply;
    }

    public void setQuantity_supply(int quantity_supply) {
        this.quantity_supply = quantity_supply;
    }

    public Date getDate_production() {
        return date_production;
    }

    public void setDate_production(Date date_production) {
        this.date_production = date_production;
    }
}
