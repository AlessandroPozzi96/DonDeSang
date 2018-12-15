package com.henallux.dondesang.model;

public class GroupeSanguin {
    private String nom;

    public GroupeSanguin() {
    }

    public GroupeSanguin(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return this.nom;
    }
}
