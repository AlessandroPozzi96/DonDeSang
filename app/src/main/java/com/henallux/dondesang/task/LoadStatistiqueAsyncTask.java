package com.henallux.dondesang.task;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.henallux.dondesang.Constants;
import com.henallux.dondesang.R;
import com.henallux.dondesang.model.Statistique;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadStatistiqueAsyncTask implements Callback<Statistique> {
    private Context context;
    private EditText edit_NbDons, edit_NbViesSauvees, edit_NbUtilisateurs, edit_NbCollectes;
    private TextView nbDonsTot;

    public LoadStatistiqueAsyncTask(Context context, EditText edit_NbDons, EditText edit_NbViesSauvees, EditText edit_NbUtilisateurs, EditText edit_NbCollectes, TextView nbDonsTot) {
        this.context = context;
        this.edit_NbDons = edit_NbDons;
        this.edit_NbViesSauvees = edit_NbViesSauvees;
        this.edit_NbUtilisateurs = edit_NbUtilisateurs;
        this.edit_NbCollectes = edit_NbCollectes;
        this.nbDonsTot = nbDonsTot;
    }

    @Override
    public void onResponse(Call<Statistique> call, Response<Statistique> response) {
        if (context == null || edit_NbCollectes == null || edit_NbDons == null || edit_NbUtilisateurs == null || edit_NbViesSauvees == null)
            return;
        if (!response.isSuccessful()) {
            Toast.makeText(context, Constants.MSG_ERREUR_GENERAL, Toast.LENGTH_SHORT).show();
            return;
        }
        Statistique statistique = response.body();

        this.edit_NbDons.setText(statistique.getNbDons().toString());
        this.edit_NbViesSauvees.setText("" + (statistique.getNbDonsTot() * 3));
        this.edit_NbUtilisateurs.setText(statistique.getNbUtilisateursInscrit().toString());
        this.edit_NbCollectes.setText(statistique.getNbCollecteTot().toString());
        this.nbDonsTot.setText(this.nbDonsTot.getText() + " " + statistique.getNbDonsTot() + " " + context.getResources().getString(R.string.tot_don_de_sang));
    }

    @Override
    public void onFailure(Call<Statistique> call, Throwable t) {
        if (context != null) {
            Toast.makeText(context, Constants.MSG_ERREUR_GENERAL, Toast.LENGTH_SHORT).show();
        }
    }
}
