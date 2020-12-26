package com.example.web.app.dao.model;

public class ModelHoody {
    private int id;
    private String name_hoody;
    private int is_tested;
    private Boolean is_allowed;
    private int human_id;
    private String urlmaket;
    private String material;
    private String photo;

    private String NSP;


    public String getNSP() {
        return NSP;
    }

    public void setNSP(String NSP) {
        this.NSP = NSP;
    }



    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_hoody() {
        return name_hoody;
    }

    public void setName_hoody(String name_hoody) {
        this.name_hoody = name_hoody;
    }

    public int getIs_tested() {
        return is_tested;
    }

    public void setIs_tested(int is_tested) {
        this.is_tested = is_tested;
    }

    public Boolean getIs_allowed() {
        return is_allowed;
    }

    public void setIs_allowed(Boolean is_allowed) {
        this.is_allowed = is_allowed;
    }

    public int getHuman_id() {
        return human_id;
    }

    public void setHuman_id(int human_id) {
        this.human_id = human_id;
    }

    public String getUrlmaket() {
        return urlmaket;
    }

    public void setUrlmaket(String urlmaket) {
        this.urlmaket = urlmaket;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}


