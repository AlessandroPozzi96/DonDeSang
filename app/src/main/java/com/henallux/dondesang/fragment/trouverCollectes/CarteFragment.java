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
        locationViewModel = ViewModelProviders.of(getActivity()).get(LocationViewModel.class);
        return inflater.inflate(R.layout.fragment_carte, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView = (TextView) getView().findViewById(R.id.textView_localisation);

        textView.setText(textView.getText().toString() + locationViewModel.getLocation());
    }
}
