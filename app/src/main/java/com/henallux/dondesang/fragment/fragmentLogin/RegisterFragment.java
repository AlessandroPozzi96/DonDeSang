package com.henallux.dondesang.fragment.fragmentLogin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.henallux.dondesang.R;
import com.henallux.dondesang.Util;

public class RegisterFragment extends Fragment {

    Button buttonInscription;
    TextView editLogin;
    TextView editPassword;
    TextView editPasswordRepeat;
    TextView editEmail;

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
        editEmail = getView().findViewById(R.id.registerEditEmail);
        buttonInscription = getView().findViewById(R.id.registerButtonInscription);
        buttonInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificationDonnees();
            }
        });
    }

    public void verificationDonnees(){
        if(
                verificationLogin()
                && verificationPaswword()
                && verificationPasswordRepeat()
                && verificationEmail()
                ){
            Toast.makeText(getActivity(),"Envoie a la BD de l'inscription",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(),"Données invalides",Toast.LENGTH_SHORT).show();
        }

    }

    public boolean verificationLogin(){ // Fait juste une vérification de longeur
        if(Util.verificationLoginLongeur(editLogin)){
            return Util.verificationLoginPresent(editLogin);
        }else{
            return false;
        }
    }
    public boolean verificationPaswword(){
        return Util.verificationPassword(editPassword);
    }
    public boolean verificationPasswordRepeat(){
        return Util.verificationPasswordRepeat(editPassword,editPasswordRepeat);
    }
    public boolean verificationEmail() {
        if(Util.verificationEmail(editEmail.getText().toString())){
            if(Util.verificationEmailDansBD(editEmail.getText().toString())){
                return true;
            }else{
                editEmail.setError("Email déja utilisé");
                return false;
            }
        }else{
            editEmail.setError("Mauvais email");
            return false;
        }
    }

    @Override
    public void onAttach(Context context) {
        Log.i("register", "onAttach: ");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("register", "onCreate: ");

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.i("register", "onStart: ");

        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i("register", "onResume: ");

        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i("register", "onPause: ");

        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i("register", "onStop: ");

        super.onStop();

    }

    @Override
    public void onDestroyView() {
        Log.i("register", "onDestroyView: ");

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i("register", "onDestroy: ");

        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i("register", "OnDetach: ");

        super.onDetach();
    }
}
