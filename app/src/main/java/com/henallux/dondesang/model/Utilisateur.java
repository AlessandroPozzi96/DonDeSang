package com.henallux.dondesang.model;

import java.util.Date;

public class Utilisateur {

    private String login;
    private String password;
    private String nom;
    private String prenom;
    private String mail;
    private int GSM;
    private int poids;
    private char sexe;
    private boolean estCelibataire;
    private Date dateNaissance;
    private int registrationId;

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
}
