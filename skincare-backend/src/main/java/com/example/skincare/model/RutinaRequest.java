package com.example.skincare.model;

public class RutinaRequest {
    private Long utilizatorId;
    private Long produsId;
    private String tip;

    // Getters și Setters
    public Long getUtilizatorId() {
        return utilizatorId;
    }

    public void setUtilizatorId(Long utilizatorId) {
        this.utilizatorId = utilizatorId;
    }

    public Long getProdusId() {
        return produsId;
    }

    public void setProdusId(Long produsId) {
        this.produsId = produsId;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
