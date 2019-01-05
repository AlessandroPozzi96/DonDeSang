package com.henallux.dondesang.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.henallux.dondesang.Constants;
import com.henallux.dondesang.R;
import com.henallux.dondesang.Util;
import com.henallux.dondesang.model.Information;
import com.henallux.dondesang.model.InformationViewModel;
import com.henallux.dondesang.services.InformationService;
import com.henallux.dondesang.services.ServiceBuilder;
import com.henallux.dondesang.task.LoadInfosAsyncTask;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfosFragment extends Fragment {
    private View view;
    private TextView question1, question2, question3;
    private LinearLayout linearLayout, linearLayout_Question1, linearLayout_Question3, linearLayout_Question2;
    private Button butFaq, button_Question1, button_Question3, button_Question2;
    private FragmentManager fragmentManager;
    private InformationViewModel informationViewModel;
    private String tag = "InfosFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_infos, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearlayout_faq);

        informationViewModel = ViewModelProviders.of(getActivity()).get(InformationViewModel.class);

        linearLayout_Question1 = view.findViewById(R.id.linearLayout_Question1);
        linearLayout_Question2 = view.findViewById(R.id.linearLayout_Question2);
        linearLayout_Question3 = view.findViewById(R.id.linearLayout_Question3);


        butFaq = view.findViewById(R.id.button_FAQ);
        butFaq.setEnabled(false);
        button_Question1 = view.findViewById(R.id.button_Question1);
        button_Question2 = view.findViewById(R.id.button_Question2);
        button_Question3 = view.findViewById(R.id.button_Question3);

        MyButtonListener myButtonListener = new MyButtonListener();
        butFaq.setOnClickListener(myButtonListener);
        button_Question3.setOnClickListener(myButtonListener);
        button_Question2.setOnClickListener(myButtonListener);
        button_Question1.setOnClickListener(myButtonListener);

        //Si aucune connexion ou API hors service
        question1 = Util.stylingTextView(Constants.MSG_ERREUR_REPONSE, Constants.TAILLE_REPONSE, getContext());
        question2 = Util.stylingTextView(Constants.MSG_ERREUR_REPONSE, Constants.TAILLE_REPONSE, getContext());
        question3 = Util.stylingTextView(Constants.MSG_ERREUR_REPONSE, Constants.TAILLE_REPONSE, getContext());
        button_Question1.setText(Constants.MSG_CHARGEMENT);
        button_Question2.setText(Constants.MSG_CHARGEMENT);
        button_Question3.setText(Constants.MSG_CHARGEMENT);

        fragmentManager = getFragmentManager();

        InformationService informationService = ServiceBuilder.buildService(InformationService.class);
        Call<List<Information>> listCall = informationService.getInformations();

        listCall.enqueue(new LoadInfosAsyncTask(getContext(), butFaq, button_Question1, button_Question2, button_Question3, question1, question2, question3, informationViewModel));

        return view;
    }

    private class MyButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            linearLayout_Question1.removeAllViews();
            linearLayout_Question3.removeAllViews();
            linearLayout_Question2.removeAllViews();
            switch (v.getId())
            {
                case R.id.button_Question1 :
                    linearLayout_Question1.addView(question1);
                    break;
                case R.id.button_Question2 :
                    linearLayout_Question2.addView(question2);
                    break;
                case R.id.button_Question3 :
                    linearLayout_Question3.addView(question3);
                    break;
                case R.id.button_FAQ :
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.fragment_container,new FaqFragment(),"replaceFragmentByFaqFragment");
                    transaction.addToBackStack("InfosFragment");
                    transaction.commit();
                    break;
            }
        }
    }

}