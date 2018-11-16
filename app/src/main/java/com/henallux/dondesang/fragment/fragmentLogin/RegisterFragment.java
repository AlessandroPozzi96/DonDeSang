package com.henallux.dondesang.fragment.fragmentLogin;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.henallux.dondesang.R;

public class RegisterFragment extends Fragment {

    Button buttonInscription;
    TextView editLogin;
    TextView editPassword;
    TextView editPasswordRepeat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editLogin = getView().findViewById(R.id.registerEditLogin);
        editPassword = getView().findViewById(R.id.registerEditPassword);
        editPasswordRepeat = getView().findViewById(R.id.registerEditPasswordRepeat);
        buttonInscription = getView().findViewById(R.id.registerButtonInscription);
        buttonInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificationDonnees(editLogin,editPassword,editPasswordRepeat);
            }
        });
    }

    public void verificationDonnees(TextView editLogin,TextView editPassword, TextView editPasswordRepeat){
        verificationLogin(editLogin);


    }

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // Verifier sur le login est déja présent dans la bd ou non.
    public boolean verificationLogin(TextView editLogin){ // Fait juste une vérification de longeur
        if(editLogin.getText().length() > 4){
            if(false){ // TESTER SI LOGIN PRESENT DANS LA BD
                return true;
            }else{
                editLogin.setError("Pseudo non disponible");
                return false;
            }
        }
        else{
            editLogin.setError("Doit être supérieur à 4 caractère");
            return false;
        }
    }
}
