package com.henallux.dondesang.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.google.gson.Gson;
import com.henallux.dondesang.IMyListener;
import com.henallux.dondesang.Util;
import com.henallux.dondesang.fragment.InfosFragment;
import com.henallux.dondesang.fragment.ProfileFragment;
import com.henallux.dondesang.fragment.trouverCollectes.LocalisationFragment;
import com.henallux.dondesang.fragment.fragmentLogin.EnregistrementFragment;
import com.henallux.dondesang.fragment.NotificationsFragment;
import com.henallux.dondesang.fragment.ScoreFragment;
import com.henallux.dondesang.R;
import com.henallux.dondesang.model.Collecte;
import com.henallux.dondesang.model.Login;
import com.henallux.dondesang.model.Token;
import com.henallux.dondesang.model.Utilisateur;
import com.henallux.dondesang.services.CollecteService;
import com.henallux.dondesang.services.FireBaseMessengingService;
import com.henallux.dondesang.services.ServiceBuilder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements IMyListener {

    Token token;
    Utilisateur utilisateur;
    Login login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        String utilisateurJSONString = sharedPref.getString("utilisateurJSONString",null);
        String tokenAccessJSONString = sharedPref.getString("tokenAccessJSONString",null);
        Gson gson = new Gson();

        if(utilisateurJSONString != null){
            utilisateur = gson.fromJson(utilisateurJSONString,Utilisateur.class);
                Log.i("tag", "Voici utilisateur : " + utilisateur.getLogin());
                Log.i("tag", "Voici utilisateur : " + utilisateur.getPassword());
        }

        if(tokenAccessJSONString != null){
            token = gson.fromJson(tokenAccessJSONString,Token.class);
                Log.i("tag", "Voici token : " + token.getAccess_token());
        }

        //On configure le menu
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //Permet de rester sur le fragment sélectionner lorsqu'on change l'orientation de l'écran
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new LocalisationFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_profile:
                            if(utilisateur == null) {
                                selectedFragment = new EnregistrementFragment();
                                //selectedFragment = new ProfileFragment();
                            }else{
                                selectedFragment = new ProfileFragment();
                            }
                            break;
                        case R.id.nav_map:
                            selectedFragment = new LocalisationFragment();
                            break;
                        case R.id.nav_group:
                            selectedFragment = new ScoreFragment();
                            break;
                        case R.id.nav_faq:
                            selectedFragment = new InfosFragment();
                            break;
                        case R.id.nav_notifications:
                            selectedFragment = new NotificationsFragment();
                            break;
                    }
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, selectedFragment);
                    transaction.commit();

                    return true;
                }
            };

    @Override
    public void setToken(Token tok) {
        this.token = tok;
    }
    public Token getToken()
    {
        return this.token;
    }

    @Override
    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    @Override
    public void setUtilisateur(Utilisateur result) {
        this.utilisateur = result;
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //On quitte l'application
        } else {
            getFragmentManager().popBackStack();

        }
    }
}