package com.henallux.dondesang.model;

public class Jourouverture {
    private Integer id;
    private String libelleJour;
    private String date;
    private Integer fkCollecte;
    private TrancheHoraire fkTrancheHoraireNavigation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelleJour() {
        return libelleJour;
    }

    public void setLibelleJour(String libelleJour) {
        this.libelleJour = libelleJour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getFkCollecte() {
        return fkCollecte;
    }

    public void setFkCollecte(Integer fkCollecte) {
        this.fkCollecte = fkCollecte;
    }

    public TrancheHoraire getFkTrancheHoraireNavigation() {
        return fkTrancheHoraireNavigation;
    }

    public void setFkTrancheHoraireNavigation(TrancheHoraire fkTrancheHoraireNavigation) {
        this.fkTrancheHoraireNavigation = fkTrancheHoraireNavigation;
    }

    @Override
    public String toString() {
        return "Jourouverture{" +
                "id=" + id +
                ", libelleJour='" + libelleJour + '\'' +
                ", date=" + date +
                ", fkCollecte=" + fkCollecte +
                ", fkTrancheHoraireNavigation=" + fkTrancheHoraireNavigation +
                '}';
    }
}
