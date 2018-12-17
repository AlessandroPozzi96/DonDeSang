package com.henallux.dondesang.task;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Button;

import com.henallux.dondesang.DataAcces.CollecteDAO;
import com.henallux.dondesang.R;
import com.henallux.dondesang.exception.DeserialisationException;
import com.henallux.dondesang.model.Collecte;
import com.henallux.dondesang.model.LocationViewModel;

import java.util.ArrayList;

public class LoadCollectesAsyncTask extends AsyncTask<Void, Void, ArrayList<Collecte>> {
    private String tag = "LoadCollectesAsyncTask";
    private LocationViewModel locationViewModel;
    private Activity activity;
    private Button butCarte;

    public LoadCollectesAsyncTask(Activity activity, Button butCarte) {
        super();
        this.activity = activity;
        this.butCarte = butCarte;
        locationViewModel = ViewModelProviders.of((FragmentActivity) this.activity).get(LocationViewModel.class);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Collecte> collectes) {
        if (collectes.isEmpty()) {
            butCarte.setEnabled(false);
        }
        else
        {
            locationViewModel.setCollectes(collectes);
            butCarte.setEnabled(true);
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(ArrayList<Collecte> collectes) {
        super.onCancelled(collectes);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected ArrayList<Collecte> doInBackground(Void... voids) {
        CollecteDAO collecteDAO = new CollecteDAO();
        ArrayList<Collecte> collectes = new ArrayList<>();
        try {
            collectes = collecteDAO.getAllCollectes();
        } catch (DeserialisationException e) {
            Log.d("CollecteDAO", e.getMessage());
        } catch (Exception e) {
            Log.d("CollecteDAO", "Exception " + e.getMessage());
        }

        return collectes;
    }
}
