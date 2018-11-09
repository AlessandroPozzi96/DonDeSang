package com.henallux.dondesang.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.henallux.dondesang.R;
import com.henallux.dondesang.model.Application;
import com.henallux.dondesang.activity.CarteActivity;

public class CarteFragment extends Fragment {
    private View view;
    private Button butCarte;
    private Application applicationObject;
    private EditText codePostale;
    private Switch sharePosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_carte, container, false);

        //Récupération de la valeur du code postale
        codePostale = (EditText) view.findViewById(R.id.editText_CodePostale);

        //Lancement de la recherche de collecte lorsqu'on a une position valide
        butCarte = (Button) view.findViewById(R.id.but_RechercheCentre);
        butCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                applicationObject = (Application) getActivity().getApplicationContext();
                applicationObject.setCodePostale(codePostale.getText().toString());

                Intent intent = new Intent(getActivity(), CarteActivity.class);
                startActivity(intent);
            }
        });

        //Récupération de la position de l'utilisateur + désactivation de l'editText
        sharePosition = (Switch) view.findViewById(R.id.switch_sharePosition);
        sharePosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharePosition.isChecked())
                {
                    codePostale.setEnabled(false);
                }
                else
                {
                    codePostale.setEnabled(true);
                }
            }
        });


        return view;
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}