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
    private Integer score;
    private String ville;
    private String rue;
    private String numero;
    private String fkRole;
    private GroupeSanguin fkGroupesanguinNavigation;
    private String rv;

    public String getFkRole() {
        return fkRole;
    }

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

    public String getVille() {
        return ville;
    }

    public void setFkRole(String fkRole) {
        this.fkRole = fkRole;
    }

    public String getRue() {
        return rue;
    }

    public String getNumero() {
        return numero;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setNumGsm(Integer numGsm) {
        this.numGsm = numGsm;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setGroupeSanguin(GroupeSanguin groupeSanguin) {
        this.fkGroupesanguinNavigation = groupeSanguin;
    }

    public void setRv(String  rv) {
        rv = rv;
    }

    public String getRv() {
        return rv;
    }


}
