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
import com.henallux.dondesang.task.LoadGroupesSanguinsAsyncTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsFragment extends Fragment {
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
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        spinnerGroupesSanguins = (Spinner) getView().findViewById(R.id.combobox_groupesanguin);
        buttonValiderPreferences = (Button) getView().findViewById(R.id.button_majPreferences);
        autoriserNotifications = (Switch) getView().findViewById(R.id.switch_autoiserNotifications);
        autoriserPlaquettes = (Switch) getView().findViewById(R.id.switch_autorisationPlaquettes);
        autoriserPlasma = (Switch) getView().findViewById(R.id.switch_autorisationPlasma);

        //Fonctionnalités non implémentées
        autoriserPlaquettes.setEnabled(false);
        autoriserPlasma.setEnabled(false);

        groupesSanguins = new ArrayList<>();
        groupeChoisi = getResources().getString(R.string.groupe_sanguin_aucun);

        //Récupération des groupes sanguins via retrofit afin de ne pas hardcoder
        final GroupeSanguinService groupeSanguinService = ServiceBuilder.buildService(GroupeSanguinService.class);
        retrofit2.Call<ArrayList<GroupeSanguin>> listCall = groupeSanguinService.getGroupesSanguins();
        listCall.enqueue(new LoadGroupesSanguinsAsyncTask(getContext(), groupesSanguins, spinnerGroupesSanguins, groupeChoisi, sharedPreferences));

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
            subscribeFromOneTopic(groupeChoisi);
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
                    subscribeFromOneTopic(groupeChoisi);
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
        FirebaseMessaging.getInstance().subscribeToTopic(conversionGroupeSanguin(topic));
    }

    public void unsubscribeFromOneTopic(String topic) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(conversionGroupeSanguin(topic));
    }

    public ArrayList<GroupeSanguin> getGroupesSanguins() {
        return groupesSanguins;
    }

    public void setGroupesSanguins(ArrayList<GroupeSanguin> groupesSanguins) {
        this.groupesSanguins = groupesSanguins;
    }



    //Malheureusement Google Firebase Messenger ne prends pas en charge le caractère '+'...
    public String conversionGroupeSanguin(String groupeSanguin) {
        return groupeSanguin.replace("+", "%");
    }
}