package com.henallux.dondesang.task;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.henallux.dondesang.R;
import com.henallux.dondesang.fragment.trouverCollectes.ChoixLocaliteFragment;
import com.henallux.dondesang.model.Localite;
import com.henallux.dondesang.model.Location;
import com.henallux.dondesang.model.LocationViewModel;

import java.util.ArrayList;

public class LoadLocalitiesAsyncTask extends AsyncTask<String, Void, ArrayList<Localite>> {
    private FragmentManager fragmentManager;
    private Activity activity;
    private LocationViewModel locationViewModel;

    public LoadLocalitiesAsyncTask(FragmentManager fragmentManager, Activity activity) {
        super();
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        locationViewModel = ViewModelProviders.of((FragmentActivity) activity).get(LocationViewModel.class);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Localite> localitees) {
        //La liste des localité est récupéré
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ChoixLocaliteFragment choixLocaliteFragment = new ChoixLocaliteFragment();
        locationViewModel.setLocalities(localitees);
        transaction.replace(R.id.fragment_container,choixLocaliteFragment,"replaceFragmentByChoixLocaliteFragment");
        transaction.commit();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(ArrayList<Localite> localitees) {
        super.onCancelled(localitees);
        Log.d("onCancelled", "Call on method onCancelled() ");
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected ArrayList<Localite> doInBackground(String... strings) {
        ArrayList<Localite> localities = new ArrayList<Localite>();

        if (isCancelled()) {
            Log.d("doInBackground", "Call on method isCancelled() ");
        }
        else
        {
            int size = strings.length;
            if (size == 1) {
                try {
                    //Normalement un appel à l'API est effectué pour récupérer la liste des localités

                    localities.add(new Localite("Falmagne", new Location(4.897200, 50.199772)));
                    localities.add(new Localite("Falmignoul", new Location(4.891570, 50.203620)));
                    localities.add(new Localite("Anseremme", new Location(4.907510, 50.238370)));

                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }
            else
            {
                localities.add(new Localite("Pas de localite trouve", new Location(0, 0)));
            }
        }

        return localities;
    }
}
