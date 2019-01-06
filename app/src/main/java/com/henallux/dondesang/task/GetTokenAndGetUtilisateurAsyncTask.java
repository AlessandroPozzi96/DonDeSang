package com.henallux.dondesang.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.henallux.dondesang.Constants;
import com.henallux.dondesang.IMyListener;
import com.henallux.dondesang.R;
import com.henallux.dondesang.fragment.ProfileFragment;
import com.henallux.dondesang.model.Token;
import com.henallux.dondesang.model.Utilisateur;
import com.henallux.dondesang.services.ServiceBuilder;
import com.henallux.dondesang.services.UtilisateurService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetTokenAndGetUtilisateurAsyncTask implements Callback<Token> {
    private Activity activity;
    private FragmentManager fragmentManager;
    private String login;

    public GetTokenAndGetUtilisateurAsyncTask(Activity activity, FragmentManager fragmentManager, String login) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
        this.login = login;
    }

    @Override
    public void onResponse(Call<Token> call, Response<Token> response) {
        if (activity == null || fragmentManager == null || login == null)
            return;;
        Log.i("tag", response.toString());

        if(response.code()==200)
        {

            Token token = response.body(); // token récupérer

            Gson gson = new Gson();
            String tokenJSON = gson.toJson(token, Token.class);

            SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("tokenAccessJSONString", tokenJSON);
            editor.commit();

            ((IMyListener)activity).setToken(token);

            //Recuperer son profil
            UtilisateurService utilisateurService = ServiceBuilder.buildService(UtilisateurService.class);
            Call<Utilisateur> requete = utilisateurService.getUtilisateur("Bearer "+token.getAccess_token(), login);
            requete.enqueue(new GetUtilisateurAsyncTask(activity, fragmentManager));
        }
        else
            Toast.makeText(activity, activity.getResources().getString(R.string.erreur_credentials_connexion), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(Call<Token> call, Throwable t) {
        if (activity == null)
            return;
        Toast.makeText(activity, Constants.MSG_ERREUR_GENERAL, Toast.LENGTH_SHORT).show();
    }
}
