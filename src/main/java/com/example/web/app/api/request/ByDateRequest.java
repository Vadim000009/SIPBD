package com.example.web.app.api.request;

public class ByDateRequest {
    private int howManySupply;
    private int workshop_id;

    public int getWorkshop_id() {
        return workshop_id;
    }

    public void setWorkshop_id(int workshop_id) {
        this.workshop_id = workshop_id;
    }

    public int getHowManySupply() {
        return howManySupply;
    }

    public void setHowManySupply(int howManySupply) {
        this.howManySupply = howManySupply;
    }

}
