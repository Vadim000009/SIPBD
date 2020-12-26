package com.example.web.app.dao.model;

import java.util.Date;

public class SupplyMaterial {
    private int id;
    private int stock_id;
    private int workshop_id;
    private String quantity_supply;
    private Date date_supply;
    private String material_supply;

    public int getStock_id() {
        return stock_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWorkshop_id() {
        return workshop_id;
    }

    public void setWorkshop_id(int workshop_id) {
        this.workshop_id = workshop_id;
    }

    public String getQuantity_supply() {
        return quantity_supply;
    }

    public void setQuantity_supply(String quantity_supply) {
        this.quantity_supply = quantity_supply;
    }

    public Date getDate_supply() {
        return date_supply;
    }

    public void setDate_supply(Date date_supply) {
        this.date_supply = date_supply;
    }

    public String getMaterial_supply() {
        return material_supply;
    }

    public void setMaterial_supply(String material_supply) {
        this.material_supply = material_supply;
    }
}
