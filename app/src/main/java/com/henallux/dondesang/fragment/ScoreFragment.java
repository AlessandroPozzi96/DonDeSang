package com.henallux.dondesang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.henallux.dondesang.R;
import com.squareup.picasso.Picasso;

public class ScoreFragment extends Fragment {

    ImageView imageToShare;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_score, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageToShare = getView().findViewById(R.id.imageToShare);
        Picasso.with(getContext()).load("https://fr.cdn.v5.futura-sciences.com/buildsv6/images/mediumoriginal/1/2/e/12eac4fff4_82618_panda.jpg").into(imageToShare);
    }
}