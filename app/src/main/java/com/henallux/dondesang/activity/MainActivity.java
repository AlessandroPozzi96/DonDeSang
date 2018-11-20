package com.henallux.dondesang.activity;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.henallux.dondesang.fragment.fragmentLogin.LoginFragment;
import com.henallux.dondesang.fragment.trouverCollectes.LocalisationFragment;
import com.henallux.dondesang.fragment.fragmentLogin.EnregistrementFragment;
import com.henallux.dondesang.fragment.FavoriteFragment;
import com.henallux.dondesang.fragment.GroupFragment;
import com.henallux.dondesang.fragment.MessageFragment;
import com.henallux.dondesang.R;
import com.henallux.dondesang.model.LocationViewModel;


public class MainActivity extends AppCompatActivity {

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
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_profile:
                            if(true) {    // SI pas connecter
                                selectedFragment = new EnregistrementFragment();
                            }else{   // Si connecter : afficher le profil
                                selectedFragment = new LoginFragment();
                            }

                            break;
                        case R.id.nav_chat:
                            selectedFragment = new LocalisationFragment();
                            break;
                        case R.id.nav_group:
                            selectedFragment = new GroupFragment();
                            break;
                        case R.id.nav_message:
                            selectedFragment = new MessageFragment();
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
}