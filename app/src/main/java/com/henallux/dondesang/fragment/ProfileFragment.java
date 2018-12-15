package com.henallux.dondesang.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.henallux.dondesang.DataAcces.ApiAuthentification;
import com.henallux.dondesang.DataAcces.DataUtilisateur;
import com.henallux.dondesang.IMyListener;
import com.henallux.dondesang.R;
import com.henallux.dondesang.activity.MainActivity;
import com.henallux.dondesang.exception.ErreurConnectionException;
import com.henallux.dondesang.fragment.fragmentLogin.EnregistrementFragment;
import com.henallux.dondesang.model.Token;
import com.henallux.dondesang.model.Utilisateur;
import com.henallux.dondesang.services.ServiceBuilder;
import com.henallux.dondesang.services.UtilisateurService;

import org.json.JSONException;
import org.json.JSONObject;

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


    EditText editTextLogin;
    EditText editTexteMail;
    EditText editTextGroupeSanguin;
    EditText editTextDateNaissance;
    EditText editTextNumGSM;
    EditText editTextVille;
    EditText editTextRue;
    EditText editTextNumero;
    EditText editTextPrenom;
    EditText editTextNom;
    Button buttonVoirstat;
    Button buttonSupprimerCompte;
    Button buttonDeco;

    String erreurMessage;
    Utilisateur utilisateur;
    Token token;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
        editTextDateNaissance   = getView().findViewById(R.id.editTextDateNaissance);
        editTexteMail           = getView().findViewById(R.id.editTexteMail);
        editTextGroupeSanguin   = getView().findViewById(R.id.editTextGroupeSanguin);
        editTextLogin           = getView().findViewById(R.id.editTextLogin);
        editTextNumero          = getView().findViewById(R.id.editTextNumero);

        editTextNumGSM          = getView().findViewById(R.id.editTextNumeroGSM);

        editTextRue             = getView().findViewById(R.id.editTextRue);
        editTextVille           = getView().findViewById(R.id.editTextVille);
        editTextPrenom          = getView().findViewById(R.id.editTextPrenom);
        editTextNom             = getView().findViewById(R.id.editTextNom);
        buttonVoirstat          = getView().findViewById(R.id.buttonVoirstat);
        buttonSupprimerCompte   = getView().findViewById(R.id.buttonSupprimerCompte);
        buttonDeco = getView().findViewById(R.id.buttonDeco);
        buttonDeco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove("tokenAccessJSONString");
                editor.remove("utilisateurJSONString");
                editor.commit();

                ((IMyListener)getActivity()).setUtilisateur(null);
                ((IMyListener)getActivity()).setToken(null);

                Intent intent = new Intent(getContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        buttonSupprimerCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilisateurService utilisateurService = ServiceBuilder.buildService(UtilisateurService.class);
                Call<Void> requete = utilisateurService.deleteUtilisateur("Bearer "+token.getAccess_token(), utilisateur.getLogin());
                requete.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getContext(),"CompteSupprimer",Toast.LENGTH_LONG).show();
                            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.remove("tokenAccessJSONString");
                            editor.remove("utilisateurJSONString");
                            editor.commit();


                            Intent intent = new Intent(getContext(),MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getContext(),"Pas supprimer",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
        buttonVoirstat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void chargementDesChamps()
    {
        editTextLogin.setText(utilisateur.getLogin());
        editTextPrenom.setText(utilisateur.getPrenom());
        editTextNumGSM.setText(utilisateur.getNumGsm()+"");
        editTexteMail.setText(utilisateur.getMail());
        editTextNom.setText(utilisateur.getNom());
        editTextDateNaissance.setText(utilisateur.getDateNaissance());
    }





}