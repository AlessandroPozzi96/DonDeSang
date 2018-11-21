package com.henallux.dondesang.model;

import android.arch.lifecycle.ViewModel;

import com.henallux.dondesang.Constants;
import com.henallux.dondesang.exception.ModelException;

public class LocationViewModel extends ViewModel {

    private String codePostal;
    private Location location;
    //Surement un type model Localite à l'avenir
    private String localite;
    private boolean utiliseCodePostal;

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) throws ModelException
    {
        if (codePostal.length() != Constants.TAILLE_CODE_POSTAL_BELGIQUE) {
            throw new ModelException("LocationViewModel", "CodePostal");
        }
        this.codePostal = codePostal;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getLocalite() {
        return localite;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public boolean isUtiliseCodePostal() {
        return utiliseCodePostal;
    }

    public void setUtiliseCodePostal(boolean utiliseCodePostal) {
        this.utiliseCodePostal = utiliseCodePostal;
    }
}
