package com.henallux.dondesang.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.henallux.dondesang.IMyListener;
import com.henallux.dondesang.fragment.FaqFragment;
import com.henallux.dondesang.fragment.ProfileFragment;
import com.henallux.dondesang.fragment.trouverCollectes.LocalisationFragment;
import com.henallux.dondesang.fragment.fragmentLogin.EnregistrementFragment;
import com.henallux.dondesang.fragment.FavoriteFragment;
import com.henallux.dondesang.fragment.ScoreFragment;
import com.henallux.dondesang.R;
import com.henallux.dondesang.model.LocationViewModel;
import com.henallux.dondesang.model.Token;
import com.henallux.dondesang.task.LoadCollectesAsyncTask;
import com.henallux.dondesang.model.Utilisateur;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity implements IMyListener {

    Token token;
    Utilisateur utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // génére la clé de hash pour facebook
        //printKeyHash();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //On configure le menu
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new LocalisationFragment()).commit();
        }
        //Récupération des collectes depuis l'API
        LoadCollectesAsyncTask loadCollectesAsyncTask = new LoadCollectesAsyncTask(this);
        loadCollectesAsyncTask.execute();
    }

    /*private void printKeyHash() {
        Log.i("tag","oui");

        try{
            PackageInfo info = getPackageManager().getPackageInfo("com.henallux.dondesang.activity",
                    PackageManager.GET_SIGNATURES);
            for(Signature signature : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("tag",Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }*/

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_profile:
                            AccessToken accessToken = AccessToken.getCurrentAccessToken();
                            if(token ==null) {
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
                            selectedFragment = new FaqFragment();
                            break;
                        case R.id.nav_favorite:
                            selectedFragment = new FavoriteFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public void setToken(Token tok) {
        Log.i("tag","LE TOKEN : "+tok.getAccess_token());
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
}