package com.henallux.dondesang.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.henallux.dondesang.DataAcces.DataUtilisateur;
import com.henallux.dondesang.IMyListener;
import com.henallux.dondesang.R;
import com.henallux.dondesang.Util;
import com.henallux.dondesang.exception.ErreurConnectionException;
import com.henallux.dondesang.fragment.ProfileFragment;
import com.henallux.dondesang.model.Utilisateur;

public class changerLesDonneesAsyncTask extends AsyncTask<String, Void, Utilisateur> {

    DataUtilisateur dataUtilisateur;
    Utilisateur utilisateur;
    Utilisateur utilisateurModif;
    String erreurMessage;
    Activity activity;
    public changerLesDonneesAsyncTask(Utilisateur utilisateur, Activity activity)
    {
        this.dataUtilisateur = new DataUtilisateur();
        this.utilisateur=utilisateur;
        this.activity = activity;
    }

    @Override
    protected Utilisateur doInBackground(String... strings) {
        try {
            utilisateur.setScore(utilisateur.getScore()+100);
            utilisateurModif = dataUtilisateur.modificationUtilisateur(this.utilisateur);
            return utilisateurModif;
        } catch (Exception e) {
            erreurMessage = e.getMessage();
            return null;
        } catch (ErreurConnectionException e) {
            erreurMessage = e.getMessage();
            return null;
        }
    }

    protected void onPostExecute(Utilisateur utilisateurModif) {
        if (utilisateurModif != null) {
            /*ProfileFragment profileFragment = new ProfileFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container,profileFragment,"replaceFragmentByRegisterFragment");
            transaction.commit();*/
            IMyListener myListener = (IMyListener) activity;
            myListener.setUtilisateur(utilisateurModif);
            Log.i("tag","ok");
        } else {
            Log.i("tag","erreur");
            //Toast.makeText(context,"Erreur :"+erreurMessage, Toast.LENGTH_LONG).show();
        }
    }
}
