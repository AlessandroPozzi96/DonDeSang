package com.henallux.dondesang.task;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.henallux.dondesang.Constants;
import com.henallux.dondesang.IMyListener;
import com.henallux.dondesang.R;
import com.henallux.dondesang.model.Utilisateur;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadScoreAsyncTask implements Callback<Utilisateur> {
    private Context context;
    private Utilisateur utilisateur;
    private TextView textViewVosPoints;
    private ProgressBar progressBar;

    public LoadScoreAsyncTask(Context context, Utilisateur utilisateur, TextView textViewVosPoints, ProgressBar progressBar) {
        this.context = context;
        this.utilisateur = utilisateur;
        this.textViewVosPoints = textViewVosPoints;
        this.progressBar = progressBar;
    }

    @Override
    public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
        if (utilisateur == null || context == null || textViewVosPoints == null || progressBar == null)
            return;
        if (!response.isSuccessful()) {
            Toast.makeText(context, Constants.MSG_ERREUR_CHARGEMENT_SCORE, Toast.LENGTH_LONG).show();
            return;
        }
        utilisateur = response.body();
        ((IMyListener) context).setUtilisateur(utilisateur);
        textViewVosPoints.setText(utilisateur.getScore()+ " " + context.getResources().getString(R.string.points));
        progressBar.setProgress(utilisateur.getScore());
    }

    @Override
    public void onFailure(Call<Utilisateur> call, Throwable t) {
        if (context != null) {
            Toast.makeText(context, Constants.MSG_ERREUR_CHARGEMENT_SCORE, Toast.LENGTH_LONG).show();
        }
    }
}
