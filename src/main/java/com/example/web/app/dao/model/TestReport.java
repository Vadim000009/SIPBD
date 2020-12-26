package com.example.web.app.dao.model;

import java.util.Date;

public class TestReport {
    private int id;
    private int human_id;
    private int is_tested;
    private String reviem;
    private Date date_report;
    private String material;
    private String NSP;
    private String urlMaket;

    public String getUrlMaket() {
        return urlMaket;
    }

    public void setUrlMaket(String urlMaket) {
        this.urlMaket = urlMaket;
    }

    public String getNSP() {
        return NSP;
    }

    public void setNSP(String NSP) {
        this.NSP = NSP;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

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

    public int getIs_tested() {
        return is_tested;
    }

    public void setIs_tested(int is_tested) {
        this.is_tested = is_tested;
    }

    public String getReviem() {
        return reviem;
    }

    public void setReviem(String reviem) {
        this.reviem = reviem;
    }

    public Date getDate_report() {
        return date_report;
    }

    public void setDate_report(Date date_report) {
        this.date_report = date_report;
    }
}
