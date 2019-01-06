package com.henallux.dondesang.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.henallux.dondesang.R;
import com.henallux.dondesang.activity.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteUtilisateurAsyncTask implements Callback<Void> {
    private Activity activity;

    public DeleteUtilisateurAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {
        if (activity == null)
            return;

        if(response.isSuccessful()){
            Toast.makeText(activity,R.string.reussite_suppression_compte,Toast.LENGTH_LONG).show();
            SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove("tokenAccessJSONString");
            editor.remove("utilisateurJSONString");
            editor.commit();


            Intent intent = new Intent(activity,MainActivity.class);
            activity.startActivity(intent);
        }else{
            Toast.makeText(activity,activity.getResources().getString(R.string.echec_suppresison_compte),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {
        if (activity != null) {
            Toast.makeText(activity,activity.getResources().getString(R.string.echec_suppresison_compte),Toast.LENGTH_LONG).show();
        }
    }
}
