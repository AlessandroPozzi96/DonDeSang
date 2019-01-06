package com.henallux.dondesang.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.google.gson.Gson;
import com.henallux.dondesang.Constants;
import com.henallux.dondesang.R;
import com.henallux.dondesang.model.Token;
import com.henallux.dondesang.model.Utilisateur;
import com.henallux.dondesang.services.ServiceBuilder;
import com.henallux.dondesang.services.UtilisateurService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareImageAsyncTask implements FacebookCallback<Sharer.Result> {
    private Utilisateur utilisateur;
    private Token token;
    private TextView textViewVosPoints;
    private ProgressBar progressBar;
    private Activity activity;

    public ShareImageAsyncTask(Utilisateur utilisateur, Token token, TextView textViewVosPoints, ProgressBar progressBar, Activity activity) {
        this.utilisateur = utilisateur;
        this.token = token;
        this.textViewVosPoints = textViewVosPoints;
        this.progressBar = progressBar;
        this.activity = activity;
    }

    @Override
    public void onSuccess(Sharer.Result result) {
        if (utilisateur == null || token == null || textViewVosPoints == null || progressBar == null || activity == null)
            return;

        //new changerLesDonneesAsyncTask(utilisateur,getActivity()).execute();

        utilisateur.setScore(utilisateur.getScore()+ Constants.AJOUT_SCORE);
        UtilisateurService utilisateurService = ServiceBuilder.buildService(UtilisateurService.class);
        Call<Utilisateur> requete = utilisateurService.putUtilisateur("Bearer "+token.getAccess_token(),utilisateur.getLogin(),utilisateur);
        requete.enqueue(new LoadScoreAsyncTask(activity, utilisateur, textViewVosPoints, progressBar));

        Toast.makeText(activity,activity.getResources().getString(R.string.reussite_partage),Toast.LENGTH_LONG).show();
        Log.i("tag","sucess");
    }

    @Override
    public void onCancel() {
        if (activity != null)
            Toast.makeText(activity,R.string.annulation_partage,Toast.LENGTH_LONG).show();
        Log.i("tag","cancel");

    }

    @Override
    public void onError(FacebookException error) {
        if (activity != null)
            Toast.makeText(activity,R.string.erreur_partage,Toast.LENGTH_LONG).show();
        Log.i("tag","error : " + error.getMessage());
    }
}
