package com.henallux.dondesang.fragment.trouverCollectes;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.henallux.dondesang.R;
import com.henallux.dondesang.model.LocationViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChoixLocaliteFragment extends Fragment {
    private static ArrayList<String> localities;
    private ListView listViewLocalities;
    private FragmentManager fragmentManager;
    private ViewModel locationViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choixlocalite, container, false);

        listViewLocalities = (ListView) view.findViewById(R.id.listView_localities);
        fragmentManager = getFragmentManager();

        ArrayAdapter<String> listLocalitiesArrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                localities
        );

        listViewLocalities.setAdapter(listLocalitiesArrayAdapter);
        listViewLocalities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = (String) listViewLocalities.getItemAtPosition(position);
                locationViewModel = ViewModelProviders.of(getActivity()).get(LocationViewModel.class);
                ((LocationViewModel) locationViewModel).setLocalite(item.toString());

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

    public static ChoixLocaliteFragment newInstance(ArrayList<String> localities) {
        ChoixLocaliteFragment.localities = localities;
        ChoixLocaliteFragment choixLocaliteFragment = new ChoixLocaliteFragment();
        Bundle params = new Bundle();
        params.putStringArrayList("localities", localities);
        choixLocaliteFragment.setArguments(params);

        return choixLocaliteFragment;
    }

}
