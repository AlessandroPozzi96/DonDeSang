package com.henallux.dondesang.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.henallux.dondesang.R;
import com.henallux.dondesang.fragment.trouverCollectes.CarteFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class InfosFragment extends Fragment {
    private View view;
    private TextView textView_QuiPeutDonnerSang, textView_deroulement, textView_QuiPeutRecevoir;
    private LinearLayout linearLayout, linearLayout_QuiPeutDonnerSang, linearLayout_QuiPeutRecevoirSang, linearLayout_Deroulement;
    private Button butFaq, butQuiPeutDonner, butQuiPeutRecevoir, butDeroulement;
    private FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_infos, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearlayout_faq);

        linearLayout_QuiPeutDonnerSang = view.findViewById(R.id.linearLayout_QuiPeutDonnerSang);
        linearLayout_Deroulement = view.findViewById(R.id.linearLayout_Deroulement);
        linearLayout_QuiPeutRecevoirSang = view.findViewById(R.id.linearLayout_QuiPeutRecevoirSang);


        butFaq = view.findViewById(R.id.button_FAQ);
        butQuiPeutDonner = view.findViewById(R.id.button_Qui_Peut_Donner_Sang);
        butDeroulement = view.findViewById(R.id.button_Déroulement_Don_Sang);
        butQuiPeutRecevoir = view.findViewById(R.id.button_Qui_Peut_Recevoir_Sang3);

        MyButtonListener myButtonListener = new MyButtonListener();
        butFaq.setOnClickListener(myButtonListener);
        butQuiPeutRecevoir.setOnClickListener(myButtonListener);
        butDeroulement.setOnClickListener(myButtonListener);
        butQuiPeutDonner.setOnClickListener(myButtonListener);

        textView_QuiPeutDonnerSang = stylingTextView("Toute personne âgée de 18 à 65 ans révolus peut donner du sang. Le don de sang est également autorisé pour toute personne âgée de 66 ans à 70 ans révolus à condition que son dernier don remonte à moins de trois ans. Le premier don de sang doit avoir eu lieu avant le jour du 66ème anniversaire. Avant chaque don, on vérifie que le donneur est en condition pour donner du sang. Le médecin contrôle entre autres le poids qui doit être supérieur à 50 kilos, et la tension artérielle. Le donneur doit également remplir un questionnaire permettant au médecin de s'assurer de son bon état de santé.", 16);
        textView_deroulement = stylingTextView("Le prélèvement proprement dit dure de 5 à 10 minutes. Compte tenu de l'inscription, de l'examen médical et du temps de repos après le don, il faut prévoir une demi-heure environ.", 16);
        textView_QuiPeutRecevoir = stylingTextView("Avec une seule poche de sang, nous obtenons en réalité 3 produits différents : des globules rouges, des plaquettes et du plasma. Chacun de ces produits peut être nécessaire au patient en fonction de sa maladie ou de son traitement.\n" +
                "\n" +
                "D’une manière générale, les profils suivants peuvent avoir besoin de l’un ou l’autre de ces produits :\n" +
                "\n" +
                "Les accidentés de la route\n" +
                "Les personnes subissant une opération chirurgicale\n" +
                "Les personnes atteintes d’une leucémie\n" +
                "Les hémophiles\n" +
                "Les grands brûlés\n", 16);

        fragmentManager = getFragmentManager();

        return view;
    }

    public ArrayList<String> getQuiPeutDonnerSang() {
        return new ArrayList<String>(
                Arrays.asList("Qui peut donner du sang ?", "Toute personne âgée de 18 à 65 ans révolus peut donner du sang. Le don de sang est également autorisé pour toute personne âgée de 66 ans à 70 ans révolus à condition que son dernier don remonte à moins de trois ans. Le premier don de sang doit avoir eu lieu avant le jour du 66ème anniversaire. Avant chaque don, on vérifie que le donneur est en condition pour donner du sang. Le médecin contrôle entre autres le poids qui doit être supérieur à 50 kilos, et la tension artérielle. Le donneur doit également remplir un questionnaire permettant au médecin de s'assurer de son bon état de santé."));
    }



    private class MyButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            linearLayout_QuiPeutDonnerSang.removeAllViews();
            linearLayout_QuiPeutRecevoirSang.removeAllViews();
            linearLayout_Deroulement.removeAllViews();
            switch (v.getId())
            {
                case R.id.button_Qui_Peut_Donner_Sang :
                    linearLayout_QuiPeutDonnerSang.addView(textView_QuiPeutDonnerSang);
                    break;
                case R.id.button_Déroulement_Don_Sang :
                    linearLayout_Deroulement.addView(textView_deroulement);
                    break;
                case R.id.button_Qui_Peut_Recevoir_Sang3 :
                    linearLayout_QuiPeutRecevoirSang.addView(textView_QuiPeutRecevoir);
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