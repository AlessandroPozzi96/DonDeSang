package com.henallux.dondesang.fragment.trouverCollectes;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.location.Address;
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
import android.widget.ListView;

import com.henallux.dondesang.R;
import com.henallux.dondesang.model.LocationViewModel;

import java.util.ArrayList;

public class ChoixAddresseFragment extends Fragment {
    private ArrayList<Address> addresses;
    private ListView listViewAddresses;
    private FragmentManager fragmentManager;
    private ViewModel locationViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choixaddresse, container, false);

        locationViewModel = ViewModelProviders.of(getActivity()).get(LocationViewModel.class);
        addresses = ((LocationViewModel) locationViewModel).getAddresses();
        listViewAddresses = (ListView) view.findViewById(R.id.listView_addresses);
        fragmentManager = getFragmentManager();

        final ArrayList<String> addressesName = arrayListToArrayListString(addresses);
        ArrayAdapter<String> listLocalitiesArrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                addressesName
        );

        listViewAddresses.setAdapter(listLocalitiesArrayAdapter);
        listViewAddresses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((LocationViewModel) locationViewModel).setAddress(addresses.get(position));

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

    public ArrayList<String> arrayListToArrayListString(ArrayList<Address> addresses) {
        ArrayList<String> addressesName = new ArrayList<>();
        String addresseName;
        for (Address address : addresses) {
            addresseName = "";

            if (address.getSubLocality() != null) {
                addresseName += address.getSubLocality() + " ";
            }
            if (address.getLocality() != null) {
                addresseName += address.getLocality() + " ";
            }
            if (address.getPostalCode() != null) {
                addresseName += address.getPostalCode() + " ";
            }
            if (address.getAdminArea() != null) {
                addresseName += address.getAdminArea() + " ";
            }
            if (address.getSubAdminArea() != null) {
                addresseName += "(" + address.getSubAdminArea() + ") ";
            }
            if (address.getPremises() != null) {
                addresseName += address.getPremises() + " ";
            }
            if (address.getCountryCode() != null) {
                addresseName += address.getCountryCode() + " ";
            }
            addressesName.add(addresseName);
        }

        return addressesName;
    }
}
