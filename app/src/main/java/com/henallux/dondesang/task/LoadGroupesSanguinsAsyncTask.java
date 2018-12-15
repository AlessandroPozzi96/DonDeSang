package com.henallux.dondesang.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Spinner;

import com.henallux.dondesang.DataAcces.GroupeSanguinDAO;
import com.henallux.dondesang.exception.DeserialisationException;
import com.henallux.dondesang.model.GroupeSanguin;
import com.henallux.dondesang.model.NotificationViewModel;

import java.util.ArrayList;

public class LoadGroupesSanguinsAsyncTask extends AsyncTask<Void, Void, ArrayList<GroupeSanguin>> {
    private String tag = "LoadGroupesSanguinsAsyncTask";
    private NotificationViewModel notificationViewModel;
    private Activity activity;

    public LoadGroupesSanguinsAsyncTask(Activity activity) {
        super();
        this.activity = activity;
        this.notificationViewModel = ViewModelProviders.of((FragmentActivity) this.activity).get(NotificationViewModel.class);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<GroupeSanguin> groupeSanguins) {
        notificationViewModel.setGroupesSanguins(groupeSanguins);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(ArrayList<GroupeSanguin> groupeSanguins) {
        super.onCancelled(groupeSanguins);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected ArrayList<GroupeSanguin> doInBackground(Void... voids) {
        GroupeSanguinDAO groupeSanguinDAO = new GroupeSanguinDAO();
        ArrayList<GroupeSanguin> groupesSanguins = new ArrayList<>();

        try {
            groupesSanguins = groupeSanguinDAO.getAllGroupesSanguin();
        } catch (DeserialisationException e) {
            Log.d(tag, "DeserialisationException : " + e.getMessage());
        } catch (Exception e) {
            Log.d(tag, "Exception : " + e.getMessage());
        }

        return groupesSanguins;
    }
}
