package com.henallux.dondesang.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henallux.dondesang.Constants;
import com.henallux.dondesang.DataAcces.ApiAuthentification;
import com.henallux.dondesang.DataAcces.DataUtilisateur;
import com.henallux.dondesang.IMyListener;
import com.henallux.dondesang.R;
import com.henallux.dondesang.Util;
import com.henallux.dondesang.activity.MainActivity;
import com.henallux.dondesang.exception.ErreurConnectionException;
import com.henallux.dondesang.fragment.fragmentLogin.EnregistrementFragment;
import com.henallux.dondesang.model.Adresse;
import com.henallux.dondesang.model.GroupeSanguin;
import com.henallux.dondesang.model.Token;
import com.henallux.dondesang.model.Utilisateur;
import com.henallux.dondesang.services.ServiceBuilder;
import com.henallux.dondesang.services.UtilisateurService;
import com.henallux.dondesang.task.UpdateUtilisateurAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    /*
    !!!!!!!!!!!!
    Vous pouvez déterminer si quelqu’un est actuellement connecté en vérifiant AccessToken.getCurrentAccessToken() et Profile.getCurrentProfile().
     */
    /*
    Si vous ajoutez le bouton à un Fragment, vous devez également mettre à jour votre activité pour utiliser votre fragment. Vous pouvez personnaliser les propriétés de Login button et enregistrer un rappel dans votre méthode onCreate() ou onCreateView(). Les propriétés que vous pouvez personnaliser comprennent LoginBehavior, DefaultAudience, ToolTipPopup.Style et les autorisations associées à LoginButton. Par exemple :
*/
    DataUtilisateur dataUtilisateur;

    private Spinner spinnerGroupesSanguins2;
    GroupeSanguin groupeSanguin;
    EditText editTextLogin;
    EditText editTexteMail;
    EditText editTextGroupeSanguin;
    EditText editTextDateNaissance;
    DatePicker datePicker;
    EditText editTextNumGSM;
    EditText editTextVille;
    EditText editTextRue;
    EditText editTextNumero;
    EditText editTextPrenom;
    EditText editTextNom;
    EditText editTextPassword;
    Button buttonVoirstat;
    Button buttonSupprimerCompte;
    Button buttonDeco;
    Button buttonChangerDonne;
    ArrayList<GroupeSanguin> groupesSanguins;


    String erreurMessage;
    Utilisateur utilisateur;
    Token token;
    SharedPreferences sharedPreferences;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        this.groupesSanguins = getGroupesSanguins();

        dataUtilisateur = new DataUtilisateur();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        utilisateur = ((IMyListener) getActivity()).getUtilisateur();
        token = ((IMyListener) getActivity()).getToken();
        chargementDesChamps();

    }

    private void initialize() {
        groupeSanguin = new GroupeSanguin();

        //editTextDateNaissance   = getView().findViewById(R.id.editTextDateNaissance);
        datePicker              = getView().findViewById(R.id.datePicker);
        editTexteMail           = getView().findViewById(R.id.editTexteMail);
        editTextLogin           = getView().findViewById(R.id.editTextLogin);
        editTextLogin.setEnabled(false);

        editTextNumero          = getView().findViewById(R.id.editTextNumero);

        editTextNumGSM          = getView().findViewById(R.id.editTextNumeroGSM);
        editTextPassword        = getView().findViewById(R.id.editTextPassword);
        editTextRue             = getView().findViewById(R.id.editTextRue);
        editTextVille           = getView().findViewById(R.id.editTextVille);
        editTextPrenom          = getView().findViewById(R.id.editTextPrenom);
        editTextNom             = getView().findViewById(R.id.editTextNom);
        buttonVoirstat          = getView().findViewById(R.id.buttonVoirstat);
        spinnerGroupesSanguins2 = getView().findViewById(R.id.combobox_groupesanguin2);

        ArrayAdapter<GroupeSanguin> adapter = new ArrayAdapter<GroupeSanguin>(getContext(),  android.R.layout.simple_spinner_dropdown_item, groupesSanguins);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinnerGroupesSanguins2.setAdapter(adapter);

        spinnerGroupesSanguins2.setSelection(groupesSanguins.indexOf(sharedPreferences.getString("groupeSanguin", "AB+")));


        buttonChangerDonne  = getView().findViewById(R.id.ChangerDonne);

        spinnerGroupesSanguins2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position !=0) {
                    utilisateur.setFkGroupesanguin(parent.getItemAtPosition(position).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        buttonSupprimerCompte   = getView().findViewById(R.id.buttonSupprimerCompte);

        buttonChangerDonne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificationDonnees()) {
                    utilisateur.setPrenom(editTextPrenom.getText().toString());
                    utilisateur.setNom(editTextNom.getText().toString());
                    utilisateur.setMail(editTexteMail.getText().toString());
                    //utilisateur.setDateNaissance(editTextDateNaissance.getText().toString());
                    if (!editTextNumGSM.getText().toString().equals("")) {
                        utilisateur.setNumGsm(Integer.parseInt(editTextNumGSM.getText().toString()));
                    }
                    int mois = datePicker.getMonth() + 1;
                    utilisateur.setDateNaissance(datePicker.getYear() + "-" + mois + "-" + datePicker.getDayOfMonth());

                    utilisateur.setRue(editTextRue.getText().toString());
                    utilisateur.setNumero(editTextNumero.getText().toString());
                    utilisateur.setVille(editTextVille.getText().toString());

                    if(editTextPassword.getText().toString().equals("")){
                        utilisateur.setPassword(null);
                    }else{
                        utilisateur.setPassword(editTextPassword.getText().toString());
                    }

                    Gson gson= new Gson();


                    Log.i("tag","utilisateur refait :"+gson.toJson(utilisateur,Utilisateur.class));

                    //utilisateur.setGroupeSanguin(groupeSanguin);
                    final UtilisateurService utilisateurService = ServiceBuilder.buildService(UtilisateurService.class);

                    Call<Utilisateur> requete = utilisateurService.putUtilisateur("Bearer " + token.getAccess_token(), utilisateur.getLogin(), utilisateur);
                    requete.enqueue(new UpdateUtilisateurAsyncTask(getActivity(), utilisateur, token));
                }else{
                    Toast.makeText(getContext(),"Champ incorect",Toast.LENGTH_LONG);
                }
            }
        });
        buttonDeco = getView().findViewById(R.id.buttonDeco);
        buttonDeco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove("tokenAccessJSONString");
                editor.remove("utilisateurJSONString");
                editor.apply();

                ((IMyListener)getActivity()).setUtilisateur(null);
                ((IMyListener)getActivity()).setToken(null);

                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                //Supprimer tous les fragments dans le backstack
                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        buttonSupprimerCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle(R.string.suppression);
                builder.setMessage(R.string.etes_vous_sur);
                builder.setPositiveButton(R.string.confirmation,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SupprimerCompte();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        buttonVoirstat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (utilisateur != null)
                {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, new StatistiqueFragment(),"replaceFragmentByStatistiqueFragment");
                    transaction.addToBackStack("LoginFragment");
                    transaction.commit();
                }
            }
        });
    }

    private void SupprimerCompte() {
        UtilisateurService utilisateurService = ServiceBuilder.buildService(UtilisateurService.class);
        Call<Void> requete = utilisateurService.deleteUtilisateur("Bearer "+token.getAccess_token(), utilisateur.getLogin());
        requete.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(),R.string.reussite_suppression_compte,Toast.LENGTH_LONG).show();
                    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.remove("tokenAccessJSONString");
                    editor.remove("utilisateurJSONString");
                    editor.commit();


                    Intent intent = new Intent(getContext(),MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(),R.string.echec_suppresison_compte,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void chargementDesChamps()
    {
        editTextLogin.setText(utilisateur.getLogin());
        editTextNom.setText(utilisateur.getNom());
        editTextPrenom.setText(utilisateur.getPrenom());
        if(utilisateur.getNumGsm() != null) {
            editTextNumGSM.setText(utilisateur.getNumGsm()+"");
        }
        editTexteMail.setText(utilisateur.getMail());
        editTextNumero.setText(utilisateur.getNumero());
        editTextRue.setText(utilisateur.getRue());
        editTextVille.setText(utilisateur.getVille());


        if(utilisateur.getFkGroupesanguin()!=null) {
            for (int i = 0; i < groupesSanguins.size(); i++) {
                if (groupesSanguins.get(i).getNom().equals(utilisateur.getFkGroupesanguin())) {
                    spinnerGroupesSanguins2.setSelection(i);
                }
            }
        }

        if(utilisateur.getDateNaissance() != null){
            String [] leSplit = utilisateur.getDateNaissance().split("-");
            String [] lautreSplit = leSplit[2].split("T");
            datePicker.init(1995,1,1,null);
            datePicker.init(Integer.parseInt(leSplit[0]),Integer.parseInt(leSplit[1])-1,Integer.parseInt(lautreSplit[0]),null);
        }else{
            datePicker.init(1995,1,1,null);
        }

    }

    public ArrayList<GroupeSanguin> getGroupesSanguins()
    {
        ArrayList<GroupeSanguin> groupesSanguins = new ArrayList<>(
                Arrays.asList(
                        new GroupeSanguin("inconnu"),
                        new GroupeSanguin("O-"),
                        new GroupeSanguin("O+"),
                        new GroupeSanguin("A-"),
                        new GroupeSanguin("A+"),
                        new GroupeSanguin("AB-"),
                        new GroupeSanguin("AB+"))
        );
        return groupesSanguins;
    }


    public boolean verificationDonnees(){

        boolean loginValide     = verificationLogin();
        //boolean passwordValide = verificationPaswword();
        boolean emailValide     = verificationEmail();
        boolean numGSMnValide   = verificationNumGSM();
        boolean villeValide     = verificationVille();
        boolean rueValide       = verificationRue();
        boolean numeroValide    = verificationNumero();
        boolean nomValide       = verificationNom();
        boolean prenomValide    = verificationPrenom();
        return nomValide && prenomValide && loginValide && emailValide && numGSMnValide && villeValide && rueValide && numeroValide;
    }

    private boolean verificationPrenom() {
        return verificationTailleMinimal(editTextPrenom,2);
    }

    private boolean verificationNom() {
        return verificationTailleMinimal(editTextNom,2);
    }

    public boolean verificationLogin(){ // Fait juste une vérification de longeur
        return verificationTailleMinimal(editTextLogin,3);
    }

    /*public boolean verificationPaswword(){
        return verificationTailleMinimal(,8);
    }

    public boolean verificationPasswordRepeat(){
        String messageErreur = Util.verificationPasswordRepeat(editPassword.getText().toString(),editPasswordRepeat.getText().toString());
        if(messageErreur==null){
            return true;
        }else{
            editPasswordRepeat.setError(messageErreur);
            return false;
        }
    }*/
    public boolean verificationEmail() {
        String messageErreur = Util.verificationEmail(editTexteMail.getText().toString(), getContext());
        if(messageErreur==null){
            return true;
        }else{
            editTexteMail.setError(messageErreur);
            return false;
        }
    }
    public boolean verificationNumGSM() {
        String messageErreur = Util.verificationTailleIntervale(editTextNumGSM.getText().toString(),8,13, getContext());
        if(messageErreur==null){
            return true;
        }else{
            editTextNumGSM.setError(messageErreur);
            return false;
        }
    }
    public boolean verificationVille() {
        return verificationTailleMinimal(editTextVille,2);
    }
    public boolean verificationRue() {
        return verificationTailleMinimal(editTextRue,2);
    }
    public boolean verificationNumero() {

        String messageErreur = Util.verificationRegex(editTextNumero.getText().toString(),Constants.REGEX_NUMERO_MAISON, getContext());
        if(messageErreur == null){
            return true;
        }else{
            editTextNumero.setError(messageErreur);
            return false;
        }
    }
    public boolean verificationPaswword(){
        String messageErreur = Util.verificationTailleminimal(editTextPassword.getText().toString(),8, getContext());
        if(messageErreur == null){
            return true;
        }else{
            editTextPassword.setError(messageErreur);
            return false;
        }
    }

    private boolean verificationTailleMinimal(TextView edit, int min) {
        String messageErreur = Util.verificationTailleminimal(edit.getText().toString(),min, getContext());
        if(messageErreur==null){
            return true;
        }else{
            edit.setError(messageErreur);
            return false;
        }
    }


}