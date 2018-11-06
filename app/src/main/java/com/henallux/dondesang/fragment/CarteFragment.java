package com.henallux.dondesang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.henallux.dondesang.R;
import com.henallux.dondesang.activity.CarteActivity;
import com.henallux.dondesang.activity.MainActivity;

public class CarteFragment extends Fragment {
    private View view;
    private Button butCarte;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_carte, container, false);

        //Lancement de la recherche de collecte lorsqu'on a une position valide
        butCarte = (Button) view.findViewById(R.id.but_RechercherCentre);
        butCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CarteActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}