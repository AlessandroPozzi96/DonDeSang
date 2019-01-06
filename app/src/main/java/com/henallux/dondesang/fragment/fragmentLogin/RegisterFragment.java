package com.henallux.dondesang.fragment.fragmentLogin;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.henallux.dondesang.Constants;
import com.henallux.dondesang.DataAcces.ApiAuthentification;
import com.henallux.dondesang.DataAcces.DataUtilisateur;
import com.henallux.dondesang.IMyListener;
import com.henallux.dondesang.R;
import com.henallux.dondesang.Util;
import com.henallux.dondesang.exception.ErreurConnectionException;
import com.henallux.dondesang.fragment.ProfileFragment;
import com.henallux.dondesang.model.Login;
import com.henallux.dondesang.model.Token;
import com.henallux.dondesang.model.Utilisateur;
import com.henallux.dondesang.services.AuthenticationService;
import com.henallux.dondesang.services.ServiceBuilder;
import com.henallux.dondesang.services.UtilisateurService;
import com.henallux.dondesang.task.CreateUserAsyncTask;
import com.henallux.dondesang.task.CreateUtilisateurAsyncTask;

import javax.security.auth.callback.Callback;

import retrofit2.Call;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    Button buttonInscription;
    TextView editLogin;
    TextView editPassword;
    TextView editPasswordRepeat;
    TextView editEmail;
    TextView editNumGSM;
    TextView editVille;
    TextView editRue;
    TextView editNumero;
    TextView editNom;
    TextView editPrenom;

    String erreurMessage;
    DatePicker datePicker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recuperationDesVues();
        buttonInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificationDonnees()){
                    inscription();
                }else{
                    Toast.makeText(getActivity(),getResources().getString(R.string.champs_invalide),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inscription() {
        Utilisateur newUtilisateur = new Utilisateur();
        newUtilisateur.setLogin(editLogin.getText().toString());
        newUtilisateur.setMail(editEmail.getText().toString());
        newUtilisateur.setPassword(editPassword.getText().toString());
        newUtilisateur.setNumero(editNumero.getText().toString());
        newUtilisateur.setVille(editVille.getText().toString());
        newUtilisateur.setRue(editRue.getText().toString());
        if(! editNumGSM.getText().toString().equals("")) {
            newUtilisateur.setNumGsm(Integer.parseInt(editNumGSM.getText().toString()));
        }
        newUtilisateur.setPrenom(editPrenom.getText().toString());
        newUtilisateur.setNom(editNom.getText().toString());
        newUtilisateur.setFkRole("USER"); // on ne peut créer que des simple user depuis le mobil

        int mois = datePicker.getMonth() + 1;
        newUtilisateur.setDateNaissance(datePicker.getYear() + "-" + mois + "-" + datePicker.getDayOfMonth());



        //new CreateUserAsyncTask(utilisateur,getActivity(),getFragmentManager(),getContext()).execute();
        Log.i("tag", newUtilisateur.toString());

        Gson gson = new Gson();
        Log.i("tag",gson.toJson(newUtilisateur,Utilisateur.class));

        UtilisateurService utilisateurService = ServiceBuilder.buildService(UtilisateurService.class);
        Call<Utilisateur> createRequest = utilisateurService.createUtilisateur(newUtilisateur);
        createRequest.enqueue(new CreateUtilisateurAsyncTask(getActivity(), getFragmentManager(),newUtilisateur.getPassword()));
    }

    private void recuperationDesVues() {
        editNom = getView().findViewById(R.id.registerEditNom);
        editPrenom = getView().findViewById(R.id.registerEditPrenom);

        editLogin = getView().findViewById(R.id.registerEditLogin);
        editPassword = getView().findViewById(R.id.registerEditPassword);
        editPasswordRepeat = getView().findViewById(R.id.registerEditPasswordRepeat);
        editEmail = getView().findViewById(R.id.registerEditEmail);
        editVille = getView().findViewById(R.id.registerEditVille);
        editRue = getView().findViewById(R.id.registerEditRue);
        editNumero = getView().findViewById(R.id.registerEditNumero);
        datePicker = getView().findViewById(R.id.registerDatePicker);
        editNumGSM = getView().findViewById(R.id.registerEditNumGSM);
        buttonInscription = getView().findViewById(R.id.registerButtonInscription);
    }

    public boolean verificationDonnees(){

        boolean loginValide = verificationLogin();
        boolean passwordValide = verificationPaswword();
        boolean passwordRepeatValide = verificationPasswordRepeat();
        boolean emailValide = verificationEmail();
        boolean numGSMnValide = verificationNumGSM();
        boolean villeValide = verificationVille();
        boolean rueValide = verificationRue();
        boolean numeroValide = verificationNumero();
        boolean nomValide = verificationNom();
        boolean prenomValide = verificationPrenom();
        return nomValide && prenomValide && loginValide && passwordValide && passwordRepeatValide && emailValide && numGSMnValide && villeValide && rueValide && numeroValide;
    }

    private boolean verificationPrenom() {
        return verificationTailleMinimal(editPrenom,2);
    }

    private boolean verificationNom() {
        return verificationTailleMinimal(editNom,2);
    }

    public boolean verificationLogin(){ // Fait juste une vérification de longeur
        return verificationTailleMinimal(editLogin,3);
    }
    public boolean verificationPaswword(){
        return verificationTailleMinimal(editPassword,8);
    }

    public boolean verificationPasswordRepeat(){
        String messageErreur = Util.verificationPasswordRepeat(editPassword.getText().toString(),editPasswordRepeat.getText().toString());
        if(messageErreur==null){
            return true;
        }else{
            editPasswordRepeat.setError(messageErreur);
            return false;
        }
    }
    public boolean verificationEmail() {
        String messageErreur = Util.verificationEmail(editEmail.getText().toString());
        if(messageErreur==null){
            return true;
        }else{
            editEmail.setError(messageErreur);
            return false;
        }
    }
    public boolean verificationNumGSM() {
        String messageErreur = Util.verificationTailleIntervale(editNumGSM.getText().toString(),8,13);
        if(messageErreur==null){
            return true;
        }else{
            editNumGSM.setError(messageErreur);
            return false;
        }
    }
    public boolean verificationVille() {
        return verificationTailleMinimal(editVille,2);
    }
    public boolean verificationRue() {
        return verificationTailleMinimal(editRue,2);
    }
    public boolean verificationNumero() {

        String messageErreur = Util.verificationRegex(editNumero.getText().toString(),Constants.REGEX_NUMERO_MAISON);
        if(messageErreur == null){
            return true;
        }else{
            editNumero.setError(messageErreur);
            return false;
        }
    }

    private boolean verificationTailleMinimal(TextView edit, int min) {
        String messageErreur = Util.verificationTailleminimal(edit.getText().toString(),min);
        if(messageErreur==null){
            return true;
        }else{
            edit.setError(messageErreur);
            return false;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("register", "onCreate: ");

        super.onCreate(savedInstanceState);
    }


}
