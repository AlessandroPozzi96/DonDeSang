package com.henallux.dondesang.model;

import java.util.Date;

public class TrancheHoraire {
    private Integer id;
    private String heureDebut;
    private String heureFin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHeureDebut() {
        return heureFormate(this.heureDebut);
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return heureFormate(this.heureFin);
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    @Override
    public String toString() {
        return "TrancheHoraire{" +
                "id=" + id +
                ", heureDebut=" + heureDebut +
                ", heureFin=" + heureFin +
                '}';
    }

    public String heureFormate(String heure) {
        String tmp;
        tmp =  heure.substring(0, 5);

        StringBuilder builder = new StringBuilder(tmp);
        builder.setCharAt(2, 'H');

        return builder.toString();
    }
}
