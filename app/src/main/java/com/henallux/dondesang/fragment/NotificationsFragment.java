package com.henallux.dondesang.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import com.henallux.dondesang.R;
import com.henallux.dondesang.model.GroupeSanguin;

import java.util.ArrayList;
import java.util.Arrays;

public class NotificationsFragment extends Fragment {
    private View view;
    private Spinner spinnerGroupesSanguins;
    private ArrayList<GroupeSanguin> groupesSanguins;
    private String groupeChoisi = "O-", tag = "NotificationsFragment";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Button buttonValiderPreferences;
    private Switch autoriserNotifications, autoriserPlaquettes, autoriserPlasma;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_notifications, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        this.groupesSanguins = getGroupesSanguins();

        spinnerGroupesSanguins = (Spinner) view.findViewById(R.id.combobox_groupesanguin);
        buttonValiderPreferences = (Button) view.findViewById(R.id.button_majPreferences);
        autoriserNotifications = (Switch) view.findViewById(R.id.switch_autoiserNotifications);
        autoriserPlaquettes = (Switch) view.findViewById(R.id.switch_autorisationPlaquettes);
        autoriserPlasma = (Switch) view.findViewById(R.id.switch_autorisationPlasma);

        ArrayAdapter<GroupeSanguin> adapter = new ArrayAdapter<GroupeSanguin>(getContext(),  android.R.layout.simple_spinner_dropdown_item, groupesSanguins);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinnerGroupesSanguins.setAdapter(adapter);
        spinnerGroupesSanguins.setSelection(getGroupeSanguin(sharedPreferences.getString("groupeSanguin", "AB+")));

        editor = sharedPreferences.edit();

        spinnerGroupesSanguins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                groupeChoisi = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        autoriserNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!autoriserNotifications.isChecked()) {
                    autoriserPlaquettes.setEnabled(false);
                    autoriserPlasma.setEnabled(false);
                    spinnerGroupesSanguins.setEnabled(false);
                }
                else
                {
                    autoriserPlaquettes.setEnabled(true);
                    autoriserPlasma.setEnabled(true);
                    spinnerGroupesSanguins.setEnabled(true);
                }
            }
        });

        buttonValiderPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("groupeSanguin", groupeChoisi);
                editor.commit();

                if (autoriserNotifications.isChecked()) {

                }
                else
                {

                }
            }
        });

        Log.d(tag, "Preference : " + sharedPreferences.getString("groupeSanguin", "O-"));

        return view;
    }


    public ArrayList<GroupeSanguin> getGroupesSanguins()
    {
        ArrayList<GroupeSanguin> groupesSanguins = new ArrayList<>(
                Arrays.asList(
                        new GroupeSanguin("O-"),
                        new GroupeSanguin("O+"),
                        new GroupeSanguin("A-"),
                        new GroupeSanguin("A+"),
                        new GroupeSanguin("AB-"),
                        new GroupeSanguin("AB+"))
        );

        return groupesSanguins;
    }

    public int getGroupeSanguin(String groupe) {
        int index = 0;
        for (int i = 0; i < getGroupesSanguins().size(); i++) {
            if (getGroupesSanguins().get(i).getNom().equals(groupe)) {
                index = i;
            }
        }
        return index;
    }
}