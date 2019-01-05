package com.henallux.dondesang.task;


import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.henallux.dondesang.R;
import com.henallux.dondesang.model.GroupeSanguin;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadGroupesSanguinsAsyncTask implements Callback<ArrayList<GroupeSanguin>> {
    private Context context;
    private ArrayList<GroupeSanguin> groupesSanguins;
    private Spinner spinnerGroupesSanguins;
    private String groupeChoisi;
    private SharedPreferences sharedPreferences;

    public LoadGroupesSanguinsAsyncTask(Context context, ArrayList<GroupeSanguin> groupesSanguins, Spinner spinnerGroupesSanguins, String groupeChoisi, SharedPreferences sharedPreferences) {
        this.context = context;
        this.groupesSanguins = groupesSanguins;
        this.spinnerGroupesSanguins = spinnerGroupesSanguins;
        this.groupeChoisi = groupeChoisi;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void onResponse(Call<ArrayList<GroupeSanguin>> call, Response<ArrayList<GroupeSanguin>> response) {
        if (context == null || groupesSanguins == null || spinnerGroupesSanguins == null || groupeChoisi == null || sharedPreferences == null)
            return;
        if (!response.isSuccessful()) {
            Toast.makeText(context, context.getResources().getString(R.string.erreur_groupeSanguin), Toast.LENGTH_SHORT).show();
            return;
        }

        groupesSanguins.add(new GroupeSanguin(context.getResources().getString(R.string.groupe_sanguin_aucun)));
        for (GroupeSanguin groupeSanguin : response.body()) {
            groupesSanguins.add(groupeSanguin);
        }

        ArrayAdapter<GroupeSanguin> adapter = new ArrayAdapter<GroupeSanguin>(context,  android.R.layout.simple_spinner_dropdown_item, groupesSanguins);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinnerGroupesSanguins.setAdapter(adapter);
        groupeChoisi = sharedPreferences.getString("groupeSanguin", "Aucun");
        spinnerGroupesSanguins.setSelection(getIndexGroupeSanguin(groupeChoisi, groupesSanguins));
    }

    @Override
    public void onFailure(Call<ArrayList<GroupeSanguin>> call, Throwable t) {
        if (context != null)
            Toast.makeText(context, context.getResources().getString(R.string.erreur_groupeSanguin), Toast.LENGTH_SHORT).show();
    }

    public int getIndexGroupeSanguin(String groupeChoisi, ArrayList<GroupeSanguin> groupesSanguins) {
        if (groupeChoisi == null || groupesSanguins == null)
            return 0;
        int indexGroupeSanguin = 0;
        for (int i = 0; i < groupesSanguins.size(); i++) {
            if (groupesSanguins.get(i).getNom().equals(groupeChoisi)) {
                indexGroupeSanguin = i;
            }
        }

        return indexGroupeSanguin;
    }
}
