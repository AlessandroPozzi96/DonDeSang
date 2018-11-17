package com.henallux.dondesang.fragment.fragmentLogin;

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
import com.henallux.dondesang.UtilTest;

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
        if(
                verificationLogin(editLogin)
                && verificationPaswword(editPassword)
                && verificationPasswordRepeat(editPassword,editPasswordRepeat)
                ){
            Toast.makeText(getActivity(),"Envoie a la BD de l'inscription",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(),"Données invalides",Toast.LENGTH_SHORT).show();
        }

    }

    public boolean verificationLogin(TextView editLogin){ // Fait juste une vérification de longeur
        return UtilTest.verificationLoginLongeur(editLogin);
    }
    public boolean verificationPaswword(TextView editPassword){
        return UtilTest.verificationPassword(editPassword);
    }
    public boolean verificationPasswordRepeat(TextView editPassword,TextView editPasswordRepeat){
        if(editPassword.getText().toString().equals(editPasswordRepeat.getText().toString())){
            return true;
        }
        else{
            editPasswordRepeat.setError("Le password n'est pas le même");
            return false;
        }
    }
}
