package com.henallux.dondesang.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.henallux.dondesang.IMyListener;
import com.henallux.dondesang.fragment.FaqFragment;
import com.henallux.dondesang.fragment.ProfileFragment;
import com.henallux.dondesang.fragment.trouverCollectes.LocalisationFragment;
import com.henallux.dondesang.fragment.fragmentLogin.EnregistrementFragment;
import com.henallux.dondesang.fragment.FavoriteFragment;
import com.henallux.dondesang.fragment.GroupFragment;
import com.henallux.dondesang.R;
import com.henallux.dondesang.model.LocationViewModel;
import com.henallux.dondesang.model.Token;
import com.henallux.dondesang.task.LoadCollectesAsyncTask;
import com.henallux.dondesang.model.Utilisateur;


public class MainActivity extends AppCompatActivity implements IMyListener {

    Token token;
    private LocationViewModel locationViewModel;
    Utilisateur utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        LoadCollectesAsyncTask loadCollectesAsyncTask = new LoadCollectesAsyncTask(this);
        loadCollectesAsyncTask.execute();

    }

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
                            selectedFragment = new GroupFragment();
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