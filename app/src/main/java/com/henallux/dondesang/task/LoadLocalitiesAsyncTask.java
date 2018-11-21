package com.henallux.dondesang.task;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.henallux.dondesang.R;
import com.henallux.dondesang.fragment.fragmentLogin.RegisterFragment;
import com.henallux.dondesang.fragment.trouverCollectes.CarteFragment;
import com.henallux.dondesang.fragment.trouverCollectes.ChoixLocaliteFragment;

import java.util.ArrayList;

public class LoadLocalitiesAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {
    private FragmentManager fragmentManager;

    public LoadLocalitiesAsyncTask(FragmentManager fragmentManager) {
        super();
        this.fragmentManager = fragmentManager;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        //La liste des localité est récupéré
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ChoixLocaliteFragment choixLocaliteFragment = ChoixLocaliteFragment.newInstance(strings);
        transaction.replace(R.id.fragment_container,choixLocaliteFragment,"replaceFragmentByChoixLocaliteFragment");
        transaction.commit();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(ArrayList<String> strings) {
        super.onCancelled(strings);
        Log.d("onCancelled", "Call on method onCancelled() ");
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        ArrayList<String> localities = new ArrayList<>();

        if (isCancelled()) {
            Log.d("doInBackground", "Call on method isCancelled() ");
        }
        else
        {
            int size = strings.length;
            if (size == 1) {
                try {
                    //Normalement un appel à l'API est effectué pour récupérer la liste des localités
                    //Créé un objet model pour les localités ?

                    localities.add("Falmagne");
                    localities.add("Falmignoul");
                    localities.add("Anseremme");
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }
            else
            {
                localities.add("Pas de localités disponible");
            }
        }

        return localities;
    }
}
