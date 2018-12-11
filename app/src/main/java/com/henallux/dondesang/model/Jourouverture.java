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

    public String getDateFormate() {
        return dateFormate(this.date);
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

    public String dateFormate(String date) {
        String tmp = date.substring(0, 10);

        String year = tmp.substring(0, 4);
        String month = tmp.substring(5, 7);
        String day = tmp.substring(8, 10);

        return day + "/" + month + "/" + year;
    }
}
