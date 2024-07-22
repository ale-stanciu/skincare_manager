package com.example.skincare.model;

import java.util.List;

public class RutinaDTO {
    private List<Produs> produseDimineata;
    private List<Produs> produseSeara;

    // Getteri și setteri
    public List<Produs> getProduseDimineata() {
        return produseDimineata;
    }

    public void setProduseDimineata(List<Produs> produseDimineata) {
        this.produseDimineata = produseDimineata;
    }

    public List<Produs> getProduseSeara() {
        return produseSeara;
    }

    public void setProduseSeara(List<Produs> produseSeara) {
        this.produseSeara = produseSeara;
    }
}
