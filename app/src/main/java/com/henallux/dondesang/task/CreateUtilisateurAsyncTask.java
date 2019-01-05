package com.henallux.dondesang.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.henallux.dondesang.IMyListener;
import com.henallux.dondesang.R;
import com.henallux.dondesang.fragment.ProfileFragment;
import com.henallux.dondesang.model.Login;
import com.henallux.dondesang.model.Token;
import com.henallux.dondesang.model.Utilisateur;
import com.henallux.dondesang.services.AuthenticationService;
import com.henallux.dondesang.services.ServiceBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateUtilisateurAsyncTask implements Callback<Utilisateur> {
    private Activity activity;
    private FragmentManager fragmentManager;

    public CreateUtilisateurAsyncTask(Activity activity, FragmentManager fragmentManager) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
        if (activity == null || fragmentManager == null)
            return;
        // Inscription faite, dirige vers le profil
        if (response.isSuccessful())
        {
            Log.i("tag","Inscription ok");
            Utilisateur utilisateur = response.body(); // RECUP l'utilisateur => l'enregistrer.

            Gson gson = new Gson();
            String utilisateurJSON = gson.toJson(utilisateur, Utilisateur.class);

            SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("utilisateurJSONString", utilisateurJSON);
            editor.commit();

            ((IMyListener) activity).setUtilisateur(utilisateur);


            // on c'est inscrit il faut le token.
            Login login = new Login(utilisateur.getLogin(), utilisateur.getPassword());
            AuthenticationService authenticationService = ServiceBuilder.buildService(AuthenticationService.class);
            final Call<Token> requete = authenticationService.getToken(login);
            requete.enqueue(new retrofit2.Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    if (response.isSuccessful()) {
                        Token token = response.body();

                        Gson gson = new Gson();
                        String tokenJSON = gson.toJson(token, Token.class);

                        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("tokenAccessJSONString", tokenJSON);
                        editor.commit();

                        ((IMyListener) activity).setToken(token);


                        ProfileFragment profileFragment = new ProfileFragment();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fragment_container, profileFragment, "replaceFragmentByRegisterFragment");
                        transaction.addToBackStack("RegisterFragment");
                        transaction.commit();
                    } else {
                        Toast.makeText(activity, response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Log.i("tag",response.message());
            try {
                Toast.makeText(activity,response.errorBody().string(),Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(activity,response.message(),Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onFailure(Call<Utilisateur> call, Throwable t) {
        Toast.makeText(activity,activity.getResources().getString(R.string.erreur_inscription),Toast.LENGTH_LONG).show();
    }
}
