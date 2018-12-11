package com.henallux.dondesang.model;

import java.net.Inet4Address;
import java.util.Date;

public class Utilisateur {

    private String login;
    private String nom;
    private String prenom;
    private String password;
    private String mail;
    private Integer numGsm;
    private String dateNaissance;
    private Boolean isMale;
    private Integer score;
    private String fkRole;
    private Integer fkAdresse;
    private String fkGroupesanguin;
    private String rv;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getNumGsm() {
        return numGsm;
    }

    public Integer getScore() {
        return score;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
