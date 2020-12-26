package com.example.web.app.api.request;

import java.math.BigDecimal;

public class ByDepRequest {
    private int id;
    private int numberofwork;
    private String changedep;
    private String changepos;
    private String changepay;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChangepay() {
        return changepay;
    }

    public void setChangepay(String changepay) {
        this.changepay = changepay;
    }

    public int getNumberofwork() {
        return numberofwork;
    }

    public void setNumberofwork(int numberofwork) {
        this.numberofwork = numberofwork;
    }

    public String getChangedep() {
        return changedep;
    }

    public void setChangedep(String changedep) {
        this.changedep = changedep;
    }

    public String getChangepos() {
        return changepos;
    }

    public void setChangepos(String changepos) {
        this.changepos = changepos;
    }
}
