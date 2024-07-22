package com.example.skincare.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Rutina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long utilizatorId;

    @ManyToMany
    @JoinTable(
            name = "rutina_dimineata",
            joinColumns = @JoinColumn(name = "rutina_id"),
            inverseJoinColumns = @JoinColumn(name = "produs_id"))
    private List<Produs> produseDimineata = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "rutina_seara",
            joinColumns = @JoinColumn(name = "rutina_id"),
            inverseJoinColumns = @JoinColumn(name = "produs_id"))
    private List<Produs> produseSeara = new ArrayList<>();

    // Getteri și setteri
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUtilizatorId() {
        return utilizatorId;
    }

    public void setUtilizatorId(Long utilizatorId) {
        this.utilizatorId = utilizatorId;
    }

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
