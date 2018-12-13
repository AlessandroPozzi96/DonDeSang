package com.henallux.dondesang.fragment.fragmentLogin;

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

import com.henallux.dondesang.DataAcces.ApiAuthentification;
import com.henallux.dondesang.IMyListener;
import com.henallux.dondesang.R;
import com.henallux.dondesang.Util;
import com.henallux.dondesang.exception.ErreurConnectionException;
import com.henallux.dondesang.fragment.ProfileFragment;
import com.henallux.dondesang.model.Login;
import com.henallux.dondesang.model.Token;
import com.henallux.dondesang.model.Utilisateur;
import com.henallux.dondesang.task.GetTokenFromApiAsyncTask;

public class LoginFragment extends Fragment {
    FragmentManager fragmentManager;

    Button buttonSeConnecter;
    Button loginButtonMDPOublier;
    TextView editUserName;
    TextView editPassword;
    String erreurMessage;
    Token token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentManager = getFragmentManager();

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        buttonSeConnecter = getView().findViewById(R.id.loginButtonSeConnecter);
        loginButtonMDPOublier = getView().findViewById(R.id.loginButtonMDPOublier);
        editUserName = getView().findViewById(R.id.loginEditUserName);
        editPassword = getView().findViewById(R.id.loginEditPassword);
        buttonSeConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (verificationDonnees()) {
                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setLogin(editUserName.getText().toString());
                    utilisateur.setPassword(editPassword.getText().toString());
                    new GetTokenFromApiAsyncTask(utilisateur,getActivity(),getContext(),fragmentManager).execute();
                    } else {
                    Toast.makeText(getActivity(), "Mauvais info", Toast.LENGTH_SHORT).show();
                }
            }
        });
        loginButtonMDPOublier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MotDePasseOublieFragment motDePasseOublieFragment = new MotDePasseOublieFragment();
                FragmentManager fragmentManager;
                fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.enregistrement_container, motDePasseOublieFragment, "replaceFragmentByMotDePasseOublieFragment");
                transaction.addToBackStack("MotDePasseOublieFragment");
                transaction.commit();
            }
        });
    }

    public boolean verificationDonnees() {
        // return (verificationLogin() && verificationPassword()); Exécute pas les 2 sinons
        boolean loginOk = verificationLogin();
        boolean passwordOk = verificationPassword();
        return loginOk && passwordOk;
    }

    public boolean verificationLogin() {
        if (Util.verificationLoginLongeur(editUserName)) {
            return Util.verificationLoginDisponible(editUserName);
        } else {
            return false;
        }
    }

    public boolean verificationPassword() {
        return Util.verificationPassword(editPassword);
    }

}
