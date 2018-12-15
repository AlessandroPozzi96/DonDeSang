package com.henallux.dondesang.model;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

public class NotificationViewModel extends ViewModel {
    private ArrayList<GroupeSanguin> groupesSanguins;

    public ArrayList<GroupeSanguin> getGroupesSanguins() {
        return groupesSanguins;
    }

    public void setGroupesSanguins(ArrayList<GroupeSanguin> groupesSanguins) {
        this.groupesSanguins = groupesSanguins;
    }
}
