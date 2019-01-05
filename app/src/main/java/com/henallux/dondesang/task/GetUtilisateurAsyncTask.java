package com.henallux.dondesang.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.google.gson.Gson;
import com.henallux.dondesang.Constants;
import com.henallux.dondesang.IMyListener;
import com.henallux.dondesang.R;
import com.henallux.dondesang.fragment.ProfileFragment;
import com.henallux.dondesang.model.Utilisateur;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUtilisateurAsyncTask implements Callback<Utilisateur> {
    private Activity activity;
    private FragmentManager fragmentManager;

    public GetUtilisateurAsyncTask(Activity activity, FragmentManager fragmentManager) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
        if (activity == null || fragmentManager == null)
            return;
        if (response.isSuccessful()) {
            Utilisateur utilisateur = response.body();

            Gson gson = new Gson();
            String utilisateurJSON = gson.toJson(utilisateur, Utilisateur.class);

            SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("utilisateurJSONString", utilisateurJSON);
            editor.commit();

            ((IMyListener)activity).setUtilisateur(utilisateur);

            ProfileFragment profileFragment = new ProfileFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, profileFragment, "replaceFragmentByRegisterFragment");
            transaction.commit();
        }else{
            Toast.makeText(activity,activity.getResources().getString(R.string.connexion_impossible),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<Utilisateur> call, Throwable t) {
        if (activity == null)
            return;
        Toast.makeText(activity, Constants.MSG_ERREUR_GENERAL, Toast.LENGTH_SHORT).show();
    }
}
