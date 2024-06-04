package com.example.skincare.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.time.LocalDate;
import java.time.YearMonth;

@Entity
public class Produs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nume;
    private String firma;
    private String nuanta;
    private LocalDate dataDeschidere;
    private int valabilitate;
    private String alteDetalii;
    private String poza;


    @ManyToOne
    @JoinColumn(name = "utilizator_id")
    private Utilizator utilizator;

    // Getteri și setteri

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getNuanta() {
        return nuanta;
    }

    public void setNuanta(String nuanta) {
        this.nuanta = nuanta;
    }

    public LocalDate getDataDeschidere() {
        return dataDeschidere;
    }

    public void setDataDeschidere(LocalDate dataDeschidere) {
        this.dataDeschidere = dataDeschidere;
    }

    public int getValabilitate() {
        return valabilitate;
    }

    public void setValabilitate(int valabilitate) {
        this.valabilitate = valabilitate;
    }

    public String getAlteDetalii() {
        return alteDetalii;
    }

    public void setAlteDetalii(String alteDetalii) {
        this.alteDetalii = alteDetalii;
    }

    public String getPoza() {
        return poza;
    }

    public void setPoza(String poza) {
        this.poza = poza;
    }

    public Utilizator getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(Utilizator utilizator) {
        this.utilizator = utilizator;
    }
}
