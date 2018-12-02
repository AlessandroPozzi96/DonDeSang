package com.henallux.dondesang.model;

import java.io.Serializable;

public class Localite implements Serializable {
    private String libelle;
    private Location location;

    public Localite(String libelle, Location location) {
        setLibelle(libelle);
        setLocation(location);
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
