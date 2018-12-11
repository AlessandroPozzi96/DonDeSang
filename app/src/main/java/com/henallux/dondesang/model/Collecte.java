package com.henallux.dondesang.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collecte {

    private Integer id;
    private String nom;
    private Double latitude;
    private Double longitude;
    private Integer telephone;
    private List<Jourouverture> jourouverture;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public List<Jourouverture> getJourouverture() {
        return jourouverture;
    }

    public void setJourouverture(ArrayList<Jourouverture> jourouverture) {
        this.jourouverture = jourouverture;
    }

    public void setJourouverture(List<Jourouverture> jourouverture) {
        this.jourouverture = jourouverture;
    }

    @Override
    public String toString() {
        return "Collecte{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", telephone=" + telephone +
                ", jourouverture=" + jourouverture +
                '}';
    }
}
