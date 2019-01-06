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
import com.henallux.dondesang.model.Login;
import com.henallux.dondesang.model.Token;
import com.henallux.dondesang.model.Utilisateur;
import com.henallux.dondesang.services.AuthenticationService;
import com.henallux.dondesang.services.ServiceBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetTokenAsyncTask implements Callback<Token> {
    private Activity activity;
    private FragmentManager fragmentManager;

    public GetTokenAsyncTask(Activity activity, FragmentManager fragmentManager) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onResponse(Call<Token> call, Response<Token> response) {
        if (activity == null || fragmentManager == null)
            return;

        if (response.isSuccessful())
        {
            Token token = response.body(); // RECUP l'utilisateur => l'enregistrer.

            Gson gson = new Gson();
            String tokenJSON = gson.toJson(token, Token.class);

            SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("tokenAccessJSONString", tokenJSON);
            editor.commit();

            ((IMyListener) activity).setToken(token);

            ProfileFragment profileFragment = new ProfileFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container,profileFragment,"replaceFragmentByRegisterFragment");
            transaction.addToBackStack("LoginFragment");
            transaction.commit();

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
    public void onFailure(Call<Token> call, Throwable t) {
        Toast.makeText(activity, Constants.MSG_ERREUR_GENERAL, Toast.LENGTH_SHORT).show();
    }
}
