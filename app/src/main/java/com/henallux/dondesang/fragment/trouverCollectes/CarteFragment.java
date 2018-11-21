package com.henallux.dondesang.fragment.trouverCollectes;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.henallux.dondesang.R;
import com.henallux.dondesang.model.LocationViewModel;

public class CarteFragment extends Fragment {
    private LocationViewModel locationViewModel;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_carte, container, false);
        locationViewModel = ViewModelProviders.of(getActivity()).get(LocationViewModel.class);
        textView = (TextView) view.findViewById(R.id.textView_localisation);

        if (locationViewModel.isUtiliseCodePostal()) {
            textView.setText("Code postal : " + locationViewModel.getCodePostal() + " \n pour cette localite : " + locationViewModel.getLocalite());
        }
        else
        {
            textView.setText(locationViewModel.getLocation().toString());
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
