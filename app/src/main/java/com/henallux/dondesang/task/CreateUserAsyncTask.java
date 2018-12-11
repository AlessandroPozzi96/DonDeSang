package com.henallux.dondesang.task;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.henallux.dondesang.DataAcces.DataUtilisateur;
import com.henallux.dondesang.IMyListener;
import com.henallux.dondesang.R;
import com.henallux.dondesang.exception.ErreurConnectionException;
import com.henallux.dondesang.fragment.ProfileFragment;
import com.henallux.dondesang.model.Login;
import com.henallux.dondesang.model.Utilisateur;

public class CreateUserAsyncTask extends AsyncTask<String, Void, Utilisateur> {

    FragmentManager fragmentManager;
    Activity activity;
    Utilisateur nouvelUtilisateur;
    DataUtilisateur dataUtilisateur;
    Utilisateur utilisateur;
    String erreurMessage;
    Context context;
    public CreateUserAsyncTask(Utilisateur utilisateur, Activity activity, FragmentManager fragmentManager, Context context)
    {
        this.nouvelUtilisateur = utilisateur;
        dataUtilisateur = new DataUtilisateur();
        this.activity = activity;
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    @Override
    protected Utilisateur doInBackground(String... strings) {
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

    protected void onPostExecute(Utilisateur utilisateur) {
        if (utilisateur != null) {
            //récup le token
            new GetTokenFromApiAsyncTask(utilisateur,activity,context,fragmentManager).execute();
            //fin récup token

            ProfileFragment profileFragment = new ProfileFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container,profileFragment,"replaceFragmentByRegisterFragment");
            transaction.commit();
        } else {
            Toast.makeText(context,"Erreur :"+erreurMessage, Toast.LENGTH_LONG).show();
        }
    }


}