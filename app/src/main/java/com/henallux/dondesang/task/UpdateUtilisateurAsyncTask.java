package com.henallux.dondesang.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henallux.dondesang.IMyListener;
import com.henallux.dondesang.R;
import com.henallux.dondesang.model.Token;
import com.henallux.dondesang.model.Utilisateur;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUtilisateurAsyncTask implements Callback<Utilisateur> {
    private Activity activity;
    private Utilisateur utilisateur;
    private Token token;

    public UpdateUtilisateurAsyncTask(Activity activity, Utilisateur utilisateur, Token token) {
        this.activity = activity;
        this.utilisateur = utilisateur;
        this.token = token;
    }

    @Override
    public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
        if (utilisateur == null || token == null || activity == null) {
            return;
        }

        Gson g = new GsonBuilder().disableHtmlEscaping().create();
        String utilisateurJSON = g.toJson(utilisateur);
        Log.i("tag", utilisateurJSON);

        if (response.isSuccessful()) {
            utilisateur = response.body();

            Log.i("tag", token.getAccess_token());

            SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("utilisateurJSONString", utilisateurJSON);
            editor.commit();

            ((IMyListener) activity).setUtilisateur(utilisateur);

            Toast.makeText(activity, R.string.màj_effectué, Toast.LENGTH_LONG).show();
        } else {
            Log.i("tag", response.toString());
            Toast.makeText(activity, R.string.erreur_enregistrement, Toast.LENGTH_LONG).show();
            Log.i("tag", utilisateur.getRv());
            Log.i("tag", utilisateur.getLogin());
        }
    }

    @Override
    public void onFailure(Call<Utilisateur> call, Throwable t) {
        if (activity != null) {
            Toast.makeText(activity, R.string.erreur_enregistrement, Toast.LENGTH_LONG).show();
        }
    }
}
