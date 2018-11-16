package com.henallux.dondesang.model;

import android.arch.lifecycle.ViewModel;

import com.henallux.dondesang.Constants;
import com.henallux.dondesang.exception.ModelException;

public class LocationViewModel extends ViewModel {
    private double latitude;
    private double longitude;
    private String codePostal;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

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
}
