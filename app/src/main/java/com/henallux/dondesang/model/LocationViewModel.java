package com.henallux.dondesang.model;

import android.arch.lifecycle.ViewModel;
import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class LocationViewModel extends ViewModel {

    private LatLng location;
    private boolean utiliseAddresse;
    private ArrayList<Address> addresses;
    private Address address;
    private ArrayList<Collecte> collectes;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ArrayList<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<Address> addresses) {
        this.addresses = addresses;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public boolean isUtiliseAddresse() {
        return utiliseAddresse;
    }

    public void setUtiliseAddresse(boolean utiliseAddresse) {
        this.utiliseAddresse = utiliseAddresse;
    }

    public ArrayList<Collecte> getCollectes() {
        return collectes;
    }

    public void setCollectes(ArrayList<Collecte> collectes) {
        this.collectes = collectes;
    }
}
