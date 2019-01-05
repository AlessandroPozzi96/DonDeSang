package com.henallux.dondesang.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.henallux.dondesang.Constants;
import com.henallux.dondesang.R;
import com.henallux.dondesang.model.Information;
import com.henallux.dondesang.model.InformationViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FaqFragment extends Fragment {
    private View view;
    private LinearLayout linearLayout;
    private InformationViewModel informationViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_faq, container, false);
        linearLayout = view.findViewById(R.id.layout_faq);
        informationViewModel = ViewModelProviders.of(getActivity()).get(InformationViewModel.class);
        //On ne veut pas réafficher les 3 questions du fragment précédent
        List<Information> faq = new ArrayList<>();
        if (informationViewModel != null) {
            faq = informationViewModel.getInformations().subList(3, informationViewModel.getInformations().size());
        }
        genererFaq(faq);

        return view;
    }


    public void genererFaq(List<Information> informations) {
        TextView reponse;
        TextView question;

        for (int i = 0; i < informations.size(); i++) {
            question = new TextView(getActivity());
            question.setText(informations.get(i).getQuestion());
            question.setPadding(30, 20, 20, 30);

            reponse = new TextView(getActivity());
            reponse.setText(informations.get(i).getReponse());
            reponse.setPadding(30, 20, 20, 30);

            question.setTextSize(Constants.TAILLE_QUESTION);
            question.setGravity(Gravity.CENTER);
            question.setTypeface(null, Typeface.BOLD);

            reponse.setTextSize(Constants.TAILLE_REPONSE);
            reponse.setTypeface(null, Typeface.ITALIC);

            linearLayout.addView(question);
            linearLayout.addView(reponse);
        }
    }
}
