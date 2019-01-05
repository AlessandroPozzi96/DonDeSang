package com.henallux.dondesang.fragment.fragmentLogin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.henallux.dondesang.R;
import com.henallux.dondesang.Util;

public class MotDePasseOublieFragment extends Fragment {

    Button buttonMotDePasseOublie;
    TextView email;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mot_de_passe_oublie,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        email = getView().findViewById(R.id.mDPOublieEditEmail);
        buttonMotDePasseOublie = getView().findViewById(R.id.mDPOublieButtonReinitialiserMDP);
        buttonMotDePasseOublie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageErreur = Util.verificationEmail(email.getText().toString());
                if(messageErreur == null){
                    Toast.makeText(getActivity(), R.string.envoyer_mail + email.getText().toString(), Toast.LENGTH_SHORT).show();
                }else{
                    email.setError(messageErreur);
                }
            }
        });
    }
}
