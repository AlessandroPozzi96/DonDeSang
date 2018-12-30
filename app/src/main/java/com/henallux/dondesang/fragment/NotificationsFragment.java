package com.henallux.dondesang.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telecom.Call;
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
import com.henallux.dondesang.Constants;
import com.henallux.dondesang.R;
import com.henallux.dondesang.model.GroupeSanguin;
import com.henallux.dondesang.services.FireBaseMessengingService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.henallux.dondesang.services.GroupeSanguinService;
import com.henallux.dondesang.services.ServiceBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsFragment extends Fragment {
    private View view;
    private Spinner spinnerGroupesSanguins;
    private ArrayList<GroupeSanguin> groupesSanguins;
    private String tag = "NotificationsFragment";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Button buttonValiderPreferences;
    private Switch autoriserNotifications, autoriserPlaquettes, autoriserPlasma;
    private String groupeChoisi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_notifications, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        spinnerGroupesSanguins = (Spinner) view.findViewById(R.id.combobox_groupesanguin);
        buttonValiderPreferences = (Button) view.findViewById(R.id.button_majPreferences);
        autoriserNotifications = (Switch) view.findViewById(R.id.switch_autoiserNotifications);
        autoriserPlaquettes = (Switch) view.findViewById(R.id.switch_autorisationPlaquettes);
        autoriserPlasma = (Switch) view.findViewById(R.id.switch_autorisationPlasma);

        //Fonctionnalités non implémentées
        autoriserPlaquettes.setEnabled(false);
        autoriserPlasma.setEnabled(false);

        groupesSanguins = new ArrayList<>();
        groupeChoisi = getResources().getString(R.string.groupe_sanguin_aucun);

        //Récupération des groupes sanguins via retrofit afin de ne pas hardcoder
        final GroupeSanguinService groupeSanguinService = ServiceBuilder.buildService(GroupeSanguinService.class);
        retrofit2.Call<ArrayList<GroupeSanguin>> listCall = groupeSanguinService.getGroupesSanguins();
        listCall.enqueue(new Callback<ArrayList<GroupeSanguin>>() {
            @Override
            public void onResponse(retrofit2.Call<ArrayList<GroupeSanguin>> call, Response<ArrayList<GroupeSanguin>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), R.string.erreur_groupeSanguin, Toast.LENGTH_SHORT).show();
                    return;
                }

                groupesSanguins.add(new GroupeSanguin(getResources().getString(R.string.groupe_sanguin_aucun)));
                for (GroupeSanguin groupeSanguin : response.body()) {
                    groupesSanguins.add(groupeSanguin);
                }

                ArrayAdapter<GroupeSanguin> adapter = new ArrayAdapter<GroupeSanguin>(getContext(),  android.R.layout.simple_spinner_dropdown_item, groupesSanguins);
                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                spinnerGroupesSanguins.setAdapter(adapter);
                groupeChoisi = sharedPreferences.getString("groupeSanguin", "Aucun");
                spinnerGroupesSanguins.setSelection(getIndexGroupeSanguin(groupeChoisi));
            }

            @Override
            public void onFailure(retrofit2.Call<ArrayList<GroupeSanguin>> call, Throwable t) {
                Toast.makeText(getContext(), R.string.erreur_groupeSanguin, Toast.LENGTH_SHORT).show();
            }
        });

        editor = sharedPreferences.edit();

        spinnerGroupesSanguins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                groupeChoisi = getGroupesSanguins().get(position).getNom();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        autoriserNotifications.setChecked(sharedPreferences.getBoolean("notifications", true));
        desactiverParametres(!autoriserNotifications.isChecked());

        if (autoriserNotifications.isChecked()) {
            subscribeFromOneTopic(Constants.TOPIC_GENERAL);
            subscribeFromOneTopic(conversionGroupeSanguin(groupeChoisi));
        }

        autoriserNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (autoriserNotifications.isChecked()) {
                    desactiverParametres(false);
                }
                else
                {
                    desactiverParametres(true);
                }
            }
        });

        buttonValiderPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (autoriserNotifications.isChecked()) {
                    subscribeFromOneTopic(Constants.TOPIC_GENERAL);
                    subscribeFromOneTopic(conversionGroupeSanguin(groupeChoisi));
                }
                else
                {
                    unsubscribeFromOneTopic(Constants.TOPIC_GENERAL);
                    unsubscribeFromAllGroupesSanguins();
                }

                editor.putString("groupeSanguin", conversionGroupeSanguin(groupeChoisi));
                editor.putBoolean("notifications", autoriserNotifications.isChecked());
                editor.commit();
                Toast.makeText(getContext(), R.string.preferences_mise_a_jour, Toast.LENGTH_SHORT).show();
            }
        });

        Log.d(tag, "Preference groupe sanguin : " + sharedPreferences.getString("groupeSanguin", "Aucun"));

        return view;
    }


    public void desactiverParametres(Boolean desactiver) {

        spinnerGroupesSanguins.setEnabled(!desactiver);
    }

    public void unsubscribeFromAllGroupesSanguins() {
        if (!getGroupesSanguins().isEmpty()) {
            for (GroupeSanguin groupeSanguin : getGroupesSanguins()) {
                this.unsubscribeFromOneTopic(conversionGroupeSanguin(groupeSanguin.getNom()));
            }
        }
    }

    public void subscribeFromOneTopic(String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic);
    }

    public void unsubscribeFromOneTopic(String topic) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
    }

    public ArrayList<GroupeSanguin> getGroupesSanguins() {
        return groupesSanguins;
    }

    public void setGroupesSanguins(ArrayList<GroupeSanguin> groupesSanguins) {
        this.groupesSanguins = groupesSanguins;
    }

    public int getIndexGroupeSanguin(String groupeChoisi) {
        int indexGroupeSanguin = 0;
        for (int i = 0; i < getGroupesSanguins().size(); i++) {
            if (getGroupesSanguins().get(i).getNom().equals(groupeChoisi)) {
                indexGroupeSanguin = i;
            }
        }

        return indexGroupeSanguin;
    }

    //Malheureusement Google Firebase Messenger ne prends pas en charge le caractère '+'...
    public String conversionGroupeSanguin(String groupeSanguin) {
        return groupeSanguin.replace("+", "%");
    }
}