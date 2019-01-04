package com.henallux.dondesang.model;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Jourouverture {
    private Integer id;
    private Integer jour;
    private String date;
    private Integer fkCollecte;
    private String heureDebut;
    private String heureFin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJour() {
        return jour;
    }

    public void setJour(Integer jour) {
        this.jour = jour;
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

    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public String dateFormate() {
        String tmp = this.date.substring(0, 10);

        String year = tmp.substring(0, 4);
        String month = tmp.substring(5, 7);
        String day = tmp.substring(8, 10);

        return day + "/" + month + "/" + year;
    }

    public String heureFormate(String heure) {
        String tmp;
        tmp =  heure.substring(0, 5);

        StringBuilder builder = new StringBuilder(tmp);
        builder.setCharAt(2, 'H');

        return builder.toString();
    }
}
