package com.henallux.dondesang.task;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.henallux.dondesang.Constants;
import com.henallux.dondesang.R;
import com.henallux.dondesang.Util;
import com.henallux.dondesang.model.Information;
import com.henallux.dondesang.model.InformationViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadInfosAsyncTask implements Callback<List<Information>> {
    private Context context;
    private Button butFaq, button_Question1, button_Question2, button_Question3;
    private TextView question1, question2, question3;
    private String tag = "LoadInfosAsyncTask";
    private InformationViewModel informationViewModel;

    public LoadInfosAsyncTask(Context context, Button butFaq, Button button_Question1, Button button_Question2, Button button_Question3, TextView question1, TextView question2, TextView question3, InformationViewModel informationViewModel) {
        this.context = context;
        this.butFaq = butFaq;
        this.button_Question1 = button_Question1;
        this.button_Question2 = button_Question2;
        this.button_Question3 = button_Question3;
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
        this.informationViewModel = informationViewModel;
    }

    @Override
    public void onResponse(Call<List<Information>> call, Response<List<Information>> response) {
        if (context == null || butFaq == null || question1 == null || button_Question1 == null || question2 == null || button_Question2 == null || question3 == null || button_Question3 == null || informationViewModel == null)
        {
            Log.d(Constants.TAG_GENERAL, "LoadInfos element null");
            return;
        }

        if (!response.isSuccessful()) {
            Toast.makeText(context, context.getResources().getString(R.string.erreur_chargement_infos), Toast.LENGTH_SHORT).show();
            Log.d(tag, "Code : " + response.code());
            return;
        }

        List<Information> informations = response.body();

        question1.setText(informations.get(0).getReponse());
        button_Question1.setText(informations.get(0).getQuestion());
        question2.setText(informations.get(1).getReponse());
        button_Question2.setText(informations.get(1).getQuestion());
        question3.setText(informations.get(2).getReponse());
        button_Question3.setText(informations.get(2).getQuestion());

        //On garnit le viewModel pour le fragment FAQ
        informationViewModel.setInformations(informations);
        butFaq.setEnabled(true);
    }

    @Override
    public void onFailure(Call<List<Information>> call, Throwable t) {
        if (context != null)
            Toast.makeText(context, Constants.MSG_ERREUR_REPONSE, Toast.LENGTH_SHORT).show();
        if (butFaq == null) {
            return;
        }
        if (informationViewModel.getInformations() == null || informationViewModel.getInformations().isEmpty()) {
            butFaq.setEnabled(false);
        }
        else
        {
            butFaq.setEnabled(true);
        }
    }
}
