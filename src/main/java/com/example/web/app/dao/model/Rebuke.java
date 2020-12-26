package com.example.web.app.dao.model;

public class Rebuke {
    private int id;
    private int human_id;

    public String getRebuke() {
        return rebuke;
    }

    public void setRebuke(String rebuke) {
        this.rebuke = rebuke;
    }

    private String rebuke;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHuman_id() {
        return human_id;
    }

    public void setHuman_id(int human_id) {
        this.human_id = human_id;
    }
}
