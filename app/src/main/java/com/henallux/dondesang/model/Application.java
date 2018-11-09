package com.henallux.dondesang.model;

public class Application extends android.app.Application {
    private String codePostale;

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
}
