package com.henallux.dondesang.fragment.fragmentLogin;

import android.content.Context;
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
import com.henallux.dondesang.DataAcces.DataUtilisateur;
import com.henallux.dondesang.IMyListener;
import com.henallux.dondesang.R;
import com.henallux.dondesang.Util;
import com.henallux.dondesang.exception.ErreurConnectionException;
import com.henallux.dondesang.fragment.ProfileFragment;
import com.henallux.dondesang.model.Token;
import com.henallux.dondesang.model.Utilisateur;
import com.henallux.dondesang.task.CreateUserAsyncTask;

public class RegisterFragment extends Fragment {

    Button buttonInscription;
    TextView editLogin;
    TextView editPassword;
    TextView editPasswordRepeat;
    TextView editEmail;
    String erreurMessage;

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
            //INSCRIPTION
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setLogin(editLogin.getText().toString());
            utilisateur.setMail(editEmail.getText().toString());
            utilisateur.setPassword(editPassword.getText().toString());
            new CreateUserAsyncTask(utilisateur,getActivity(),getFragmentManager(),getContext()).execute();
        }else{
            Toast.makeText(getActivity(),"Données invalides",Toast.LENGTH_SHORT).show();
        }

    }

    public boolean verificationLogin(){ // Fait juste une vérification de longeur
        if(Util.verificationLoginLongeur(editLogin)){
            return Util.verificationLoginDisponible(editLogin);
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
        if(Util.verificationEmail(editEmail)){
            if(Util.verificationEmailDansBD(editEmail)){
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


    private class CreationUtilisateur extends AsyncTask<String, Void, Utilisateur> {


        Utilisateur nouvelUtilisateur;

        public CreationUtilisateur(Utilisateur utilisateur)
        {
            this.nouvelUtilisateur = utilisateur;
        }

        protected void onPreExecute() {
        }

        @Override
        protected Utilisateur doInBackground(String... strings) {
            Utilisateur utilisateur;
            DataUtilisateur dataUtilisateur = new DataUtilisateur();
            try {
                utilisateur = dataUtilisateur.CreationUtilisateur(this.nouvelUtilisateur);
                return utilisateur;
            } catch (Exception e) {
                erreurMessage = e.getMessage();
                return null;
            } catch (ErreurConnectionException e) {
                erreurMessage = e.getMessage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Utilisateur utilisateur) {
            if (utilisateur != null) {
                IMyListener myListener = (IMyListener) getActivity();
                myListener.setUtilisateur(utilisateur);
                FragmentManager fragmentManager = getFragmentManager();
                Toast.makeText(getContext(), utilisateur.getLogin(), Toast.LENGTH_LONG).show();
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container,profileFragment,"replaceFragmentByRegisterFragment");
                transaction.addToBackStack("RegisterFragment");
                transaction.commit();
            } else {
                Toast.makeText(getContext(),"Erreur :"+erreurMessage, Toast.LENGTH_LONG).show();
            }
        }
    }


}
