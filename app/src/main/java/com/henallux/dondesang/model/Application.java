package com.henallux.dondesang.model;

public class Application extends android.app.Application {
    private String codePostale;
    private double longitude;
    private double latitude;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String getCodePostale() {
        return codePostale;
    }

    public void setCodePostale(String codePostale) {
        this.codePostale = codePostale;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
