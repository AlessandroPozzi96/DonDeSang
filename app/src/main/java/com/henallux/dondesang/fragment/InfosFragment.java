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
import com.henallux.dondesang.model.Information;
import com.henallux.dondesang.model.InformationViewModel;
import com.henallux.dondesang.services.InformationService;
import com.henallux.dondesang.services.ServiceBuilder;

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
        question1 = stylingTextView(Constants.MSG_ERREUR_REPONSE, Constants.TAILLE_REPONSE);
        question2 = stylingTextView(Constants.MSG_ERREUR_REPONSE, Constants.TAILLE_REPONSE);
        question3 = stylingTextView(Constants.MSG_ERREUR_REPONSE, Constants.TAILLE_REPONSE);
        button_Question1.setText(Constants.MSG_CHARGEMENT);
        button_Question2.setText(Constants.MSG_CHARGEMENT);
        button_Question3.setText(Constants.MSG_CHARGEMENT);

        fragmentManager = getFragmentManager();

        InformationService informationService = ServiceBuilder.buildService(InformationService.class);
        Call<List<Information>> listCall = informationService.getInformations();

        listCall.enqueue(new Callback<List<Information>>() {
            @Override
            public void onResponse(Call<List<Information>> call, Response<List<Information>> response) {
                if (getContext() == null || butFaq == null || question1 == null || button_Question1 == null || question2 == null || button_Question2 == null || question3 == null || button_Question3 == null )
                    return;
                if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), getResources().getString(R.string.erreur_chargement_infos), Toast.LENGTH_SHORT).show();
                    Log.d(tag, "Code : " + response.code());
                    return;
                }

                List<Information> informations = response.body();
                question1 = stylingTextView(informations.get(0).getReponse(), Constants.TAILLE_REPONSE);
                button_Question1.setText(informations.get(0).getQuestion());
                question2 = stylingTextView(informations.get(1).getReponse(), Constants.TAILLE_REPONSE);
                button_Question2.setText(informations.get(1).getQuestion());
                question3 = stylingTextView(informations.get(2).getReponse(), Constants.TAILLE_REPONSE);
                button_Question3.setText(informations.get(2).getQuestion());

                //On garnit le viewModel pour le fragment FAQ
                informationViewModel.setInformations(informations);
                butFaq.setEnabled(true);
            }

            @Override
            public void onFailure(Call<List<Information>> call, Throwable t) {
                if (getContext() != null)
                    Toast.makeText(getContext(), Constants.MSG_ERREUR_REPONSE, Toast.LENGTH_SHORT).show();
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
        });

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

    public TextView stylingTextView(String text, float textSize) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(16);
        textView.setTypeface(null, Typeface.ITALIC);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setPadding(30, 20, 20, 30);
        textView.setTextSize(textSize);

        return textView;
    }
}