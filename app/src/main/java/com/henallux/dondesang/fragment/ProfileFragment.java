package com.henallux.dondesang.fragment;

import android.content.Intent;
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
import com.henallux.dondesang.exception.ErreurConnectionException;
import com.henallux.dondesang.fragment.fragmentLogin.EnregistrementFragment;
import com.henallux.dondesang.model.Token;
import com.henallux.dondesang.model.Utilisateur;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

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


    String erreurMessage;
    Utilisateur utilisateur;
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
        new getUtilisateur().execute();
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

        buttonSupprimerCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SupprimerCompte().execute();
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
        Toast.makeText(getContext(),utilisateur.getNumGsm()+"mail : "+utilisateur.getMail(),Toast.LENGTH_LONG).show();
        editTextLogin.setText(utilisateur.getLogin());
        editTextPrenom.setText(utilisateur.getPrenom());
        editTextNumGSM.setText(utilisateur.getNumGsm()+"");
        editTexteMail.setText(utilisateur.getMail());
        editTextNom.setText(utilisateur.getNom());
        editTextDateNaissance.setText(utilisateur.getDateNaissance());
    }


    private class getUtilisateur extends AsyncTask<String, Void, Utilisateur> {
        protected void onPreExecute() {
        }

        @Override
        protected Utilisateur doInBackground(String... strings) {
            Utilisateur utilisateur;

            try {
                IMyListener myListener = (IMyListener)getActivity();
                utilisateur = dataUtilisateur.getUtilisateur("Gwynbleidd",myListener.getToken());
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
        protected void onPostExecute(Utilisateur result) {
            if (result != null) {
                IMyListener myListener = (IMyListener) getActivity();
                myListener.setUtilisateur(result);
                utilisateur = result;
                chargementDesChamps();
            } else {
                Toast.makeText(getContext(),erreurMessage, Toast.LENGTH_LONG).show();
            }
        }
    }


    private class SupprimerCompte extends AsyncTask<String, Void, Boolean> {
        protected void onPreExecute() {
        }
        @Override
        protected Boolean doInBackground(String... strings) {

            try {
                IMyListener myListener = (IMyListener) getActivity();
                return dataUtilisateur.supprimerUtilisateur("Gwynbleidd",myListener.getToken());
            } catch (Exception e) {
                erreurMessage = e.getMessage();
                return false;
            } catch (ErreurConnectionException e) {
                erreurMessage = e.getMessage();
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(getContext(),"Votre Compte a été supprimer",Toast.LENGTH_LONG).show();
                IMyListener myListener = (IMyListener) getActivity();
                myListener.setToken(null);
              } else {
                Toast.makeText(getContext(),erreurMessage, Toast.LENGTH_LONG).show();
            }
        }
    }



}