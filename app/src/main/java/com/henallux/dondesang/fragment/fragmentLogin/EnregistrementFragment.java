package com.henallux.dondesang.fragment.fragmentLogin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.henallux.dondesang.DataAcces.ApiAuthentification;
import com.henallux.dondesang.R;
import com.henallux.dondesang.exception.ErreurConnectionException;
import com.henallux.dondesang.model.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class EnregistrementFragment extends Fragment {

    FragmentManager fragmentManager;
    Button buttonLogin;
    Button buttonRegister;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentManager = getFragmentManager();

        LoginFragment loginFragment = new LoginFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.enregistrement_container,loginFragment,"replaceFragmentByLoginFragment");
        transaction.commit();

        return inflater.inflate(R.layout.fragment_enregistrement,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        buttonRegister = getView().findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment registerFragment = new RegisterFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.enregistrement_container,registerFragment,"replaceFragmentByRegisterFragment");
                transaction.commit();
            }
        });

        buttonLogin = getView().findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment loginFragment = new LoginFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.enregistrement_container,loginFragment,"replaceFragmentByLoginFragment");
                transaction.commit();
            }
        });



    }
}