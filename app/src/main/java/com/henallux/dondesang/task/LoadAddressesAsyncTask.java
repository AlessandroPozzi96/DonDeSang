package com.henallux.dondesang.task;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;
import com.henallux.dondesang.R;

import com.henallux.dondesang.Constants;
import com.henallux.dondesang.fragment.trouverCollectes.ChoixAddresseFragment;
import com.henallux.dondesang.model.LocationViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LoadAddressesAsyncTask extends AsyncTask<String, Void, ArrayList<Address>> {
    private FragmentManager fragmentManager;
    private Activity activity;
    private LocationViewModel locationViewModel;
    private String tag = "LoadAddressesAsyncTask";

    public LoadAddressesAsyncTask(FragmentManager fragmentManager, Activity activity) {
        super();
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        locationViewModel = ViewModelProviders.of((FragmentActivity) activity).get(LocationViewModel.class);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Address> addresses) {
        //La liste des localité est récupéré
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ChoixAddresseFragment choixAddresseFragment = new ChoixAddresseFragment();
        locationViewModel.setAddresses(addresses);
        transaction.replace(R.id.fragment_container, choixAddresseFragment,"replaceFragmentByChoixAddresseFragment");
        transaction.commit();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(ArrayList<Address> addresses) {
        super.onCancelled(addresses);
        Log.d("onCancelled", "Call on method onCancelled() ");
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected ArrayList<Address> doInBackground(String... strings) {
        ArrayList<Address> addresses = new ArrayList<Address>();

        if (isCancelled()) {
            Log.d("doInBackground", "Call on method isCancelled() ");
        }
        else
        {
            int size = strings.length;
            if (size > 0) {
                addresses = geoLocate(strings[0]);
                Log.d(tag, "Search term : " + strings[0]);
            }
        }

        return addresses;
    }

    public ArrayList<Address> geoLocate(String searchString) {
        Geocoder geocoder = new Geocoder(activity);
        List<Address> addressList = new ArrayList<Address>();
        try {
            addressList = geocoder.getFromLocationName("" + searchString, Constants.MAX_RESULTS, 1, 1, 1, 1);
            Log.d(tag, "Taille de la liste : " + addressList.size());
        } catch (IOException e) {
            Log.d(tag, "ERREUR POUR RECUPERER LES ADDRESSES !");
        }

        ArrayList<Address> addresses;
        if (addressList.size() == 0) {
            Address address = new Address(new Locale("fr", "Belgique"));
            address.setLocality("Pas d'addresses trouvé !");
            address.setLatitude(50.503887);
            address.setLongitude(4.469936);
            addresses = new ArrayList<>(
                    Arrays.asList(address)
            );
        }
        else
            addresses = new ArrayList<Address>(addressList);

        return addresses;
    }
}
