package com.henallux.dondesang.task;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.henallux.dondesang.Constants;
import com.henallux.dondesang.model.Collecte;
import com.henallux.dondesang.model.LocationViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadCollectesAsyncTask implements Callback<List<Collecte>> {
    private Context context;
    private Button butCarte;
    private LocationViewModel locationViewModel;

    public LoadCollectesAsyncTask(Context context, Button butCarte, LocationViewModel locationViewModel) {
        this.context = context;
        this.butCarte = butCarte;
        this.locationViewModel = locationViewModel;
    }

    @Override
    public void onResponse(Call<List<Collecte>> call, Response<List<Collecte>> response) {
        if (butCarte == null || context == null || locationViewModel == null)
            return;
        if (!response.isSuccessful()) {
            butCarte.setEnabled(false);
            Toast.makeText(context, Constants.MSG_ERREUR_CHARGEMENT_COLLECTES, Toast.LENGTH_LONG).show();
            Log.d(Constants.TAG_GENERAL, "Code : " + response.code());
            return;
        }

        if (response.body().isEmpty()) {
            butCarte.setEnabled(false);
        }
        else
        {
            locationViewModel.setCollectes(response.body());
            butCarte.setEnabled(true);
        }
    }

    @Override
    public void onFailure(Call<List<Collecte>> call, Throwable t) {
        if (butCarte == null || context == null)
            return;
        butCarte.setEnabled(false);
        Toast.makeText(context, Constants.MSG_ERREUR_CHARGEMENT_COLLECTES, Toast.LENGTH_SHORT).show();
        Log.d(Constants.TAG_GENERAL, "Requête : " + call.request());
    }
}
