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
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.henallux.dondesang.R;
import com.henallux.dondesang.model.GroupeSanguin;
import com.henallux.dondesang.services.FireBaseMessengingService;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Arrays;

public class NotificationsFragment extends Fragment {
    private View view;
    private Spinner spinnerGroupesSanguins;
    private ArrayList<GroupeSanguin> groupesSanguins;
    private String tag = "NotificationsFragment", topicGeneral = "ALERTES";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Button buttonValiderPreferences;
    private Switch autoriserNotifications, autoriserPlaquettes, autoriserPlasma;
    private int groupeChoisi;

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

        //Fonctionnalités non implémentées
        autoriserPlaquettes.setEnabled(false);
        autoriserPlasma.setEnabled(false);

        ArrayAdapter<GroupeSanguin> adapter = new ArrayAdapter<GroupeSanguin>(getContext(),  android.R.layout.simple_spinner_dropdown_item, groupesSanguins);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinnerGroupesSanguins.setAdapter(adapter);
        groupeChoisi = sharedPreferences.getInt("indexGroupeSanguin", 0);
        spinnerGroupesSanguins.setSelection(groupeChoisi);

        editor = sharedPreferences.edit();

        spinnerGroupesSanguins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                groupeChoisi = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        autoriserNotifications.setChecked(sharedPreferences.getBoolean("notifications", true));
        desactiverParametres(!sharedPreferences.getBoolean("notifications", false));

        if (sharedPreferences.getBoolean("notifications", true)) {
            autoriserNotifications.setChecked(true);
            desactiverParametres(false);
            subscribeFromOneTopic(topicGeneral);
            subscribeFromOneTopic(topicGeneral + "_" + groupeChoisi);
        }

        autoriserNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!autoriserNotifications.isChecked()) {
                    desactiverParametres(true);
                }
                else
                {
                    desactiverParametres(false);
                }
            }
        });

        buttonValiderPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (autoriserNotifications.isChecked()) {
                    subscribeFromOneTopic(topicGeneral);
                    subscribeFromOneTopic(topicGeneral + "_" + groupeChoisi);
                }
                else
                {
                    unsubscribeFromOneTopic(topicGeneral);
                    unsubscribeFromAllGroupesSanguins();
                }

                //editor.putString("groupeSanguin", groupeChoisi);
                editor.putInt("indexGroupeSanguin", groupeChoisi);
                editor.putBoolean("notifications", autoriserNotifications.isChecked());
                editor.commit();
                Toast.makeText(getContext(), "Préférences mises à jour", Toast.LENGTH_SHORT).show();
            }
        });

        Log.d(tag, "Preference : " + getGroupesSanguins().get(sharedPreferences.getInt("indexGroupeSanguin", 0)));

        return view;
    }


    public ArrayList<GroupeSanguin> getGroupesSanguins()
    {
        ArrayList<GroupeSanguin> groupesSanguins = new ArrayList<>(
                Arrays.asList(
                        new GroupeSanguin("O-"),
                        new GroupeSanguin("O+"),
                        new GroupeSanguin("B-"),
                        new GroupeSanguin("B+"),
                        new GroupeSanguin("A-"),
                        new GroupeSanguin("A+"),
                        new GroupeSanguin("AB-"),
                        new GroupeSanguin("AB+"))
        );

        return groupesSanguins;
    }

    public int getGroupeSanguin(String groupe) {
        //Pas top comme solution
        int index = 0;
        for (int i = 0; i < getGroupesSanguins().size(); i++) {
            if (getGroupesSanguins().get(i).getNom().equals(groupe)) {
                index = i;
            }
        }
        return index;
    }

    public void desactiverParametres(Boolean desactiver) {

        spinnerGroupesSanguins.setEnabled(!desactiver);
    }

    public void unsubscribeFromAllGroupesSanguins() {
        for (int i = 0; i < getGroupesSanguins().size(); i++) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(topicGeneral + "_" + i);
        }
    }

    public void subscribeFromOneTopic(String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic);
    }

    public void unsubscribeFromOneTopic(String topic) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
    }
}