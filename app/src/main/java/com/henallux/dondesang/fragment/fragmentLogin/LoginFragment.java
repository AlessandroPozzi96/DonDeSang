package com.henallux.dondesang.fragment.fragmentLogin;

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

import com.henallux.dondesang.R;
import com.henallux.dondesang.UtilTest;

public class LoginFragment extends Fragment {

    Button buttonSeConnecter;
    Button loginButtonMDPOublier;
    TextView editUserName;
    TextView editPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        buttonSeConnecter = getView().findViewById(R.id.loginButtonSeConnecter);
        loginButtonMDPOublier = getView().findViewById(R.id.loginButtonMDPOublier);
        editUserName      = getView().findViewById(R.id.loginEditUserName);
        editPassword      = getView().findViewById(R.id.loginEditPassword);
        buttonSeConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),editUserName.getText().toString()+" "+editPassword.getText().toString(),Toast.LENGTH_SHORT).show();
                verificationDonnees();
                }
        });
        loginButtonMDPOublier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MotDePasseOublieFragment motDePasseOublieFragment = new MotDePasseOublieFragment();
                FragmentManager fragmentManager;
                fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.enregistrement_container,motDePasseOublieFragment,"replaceFragmentByMotDePasseOublieFragment");
                transaction.commit();
            }
        });
    }

    public void verificationDonnees(){
        verificationLogin();
        verificationPassword();
    }
    public boolean verificationLogin(){
        return UtilTest.verificationLoginLongeur(editUserName);
    }
    public boolean verificationPassword(){
        return UtilTest.verificationPassword(editPassword);
    }

}
