package com.henallux.dondesang.model;

import android.arch.lifecycle.ViewModel;

import java.util.List;

public class InformationViewModel extends ViewModel {
    private List<Information> informations;

    public List<Information> getInformations() {
        return informations;
    }

    public void setInformations(List<Information> informations) {
        this.informations = informations;
    }
}
