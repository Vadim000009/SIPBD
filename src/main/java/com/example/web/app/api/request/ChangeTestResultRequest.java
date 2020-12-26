package com.example.web.app.api.request;

public class ChangeTestResultRequest {
    private int is_tested;
    private int id;
    private String reviem;

    public int getIs_allowed() {
        return is_tested;
    }

    public void setIs_allowed(int is_tested) {
        this.is_tested = is_tested;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReviem() {
        return reviem;
    }

    public void setReviem(String reviem) {
        this.reviem = reviem;
    }
}
