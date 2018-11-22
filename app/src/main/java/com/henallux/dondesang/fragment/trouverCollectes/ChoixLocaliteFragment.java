package com.henallux.dondesang.fragment.trouverCollectes;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henallux.dondesang.R;
import com.henallux.dondesang.model.Localite;
import com.henallux.dondesang.model.LocationViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChoixLocaliteFragment extends Fragment {
    private ArrayList<Localite> localities;
    private ListView listViewLocalities;
    private FragmentManager fragmentManager;
    private ViewModel locationViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choixlocalite, container, false);

        locationViewModel = ViewModelProviders.of(getActivity()).get(LocationViewModel.class);
        localities = ((LocationViewModel) locationViewModel).getLocalities();
        listViewLocalities = (ListView) view.findViewById(R.id.listView_localities);
        fragmentManager = getFragmentManager();

        final ArrayList<String> localitiesName = arrayListLocaliteToArrayListString(localities);
        ArrayAdapter<String> listLocalitiesArrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                localitiesName
        );

        listViewLocalities.setAdapter(listLocalitiesArrayAdapter);
        listViewLocalities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((LocationViewModel) locationViewModel).setLocalite(localities.get(position));

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container,new CarteFragment(),"replaceFragmentByCarteFragment");
                transaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public ArrayList<String> arrayListLocaliteToArrayListString(ArrayList<Localite> localitiesLocalite) {
        ArrayList<String> localitiesString = new ArrayList<>();
        for (Localite localite : localitiesLocalite) {
            localitiesString.add(localite.getLibelle());
        }

        return localitiesString;
    }
}
