package com.henallux.dondesang.model;

import java.util.Date;

public class Utilisateur {

    private String login;
    private String nom;
    private String prenom;
    private String password;
    private String mail;
    private int numGsm;
    private String dateNaissance;
    private boolean isMale;
    private int score;
    private String fkRole;
    private int fkAdresse;
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

    public int getNumGsm() {
        return numGsm;
    }

    public int getScore() {
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
}
