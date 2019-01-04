package com.henallux.dondesang.fragment.trouverCollectes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.henallux.dondesang.Constants;
import com.henallux.dondesang.R;
import com.henallux.dondesang.Util;
import com.henallux.dondesang.model.Collecte;
import com.henallux.dondesang.model.Jourouverture;
import com.henallux.dondesang.model.LocationViewModel;
import com.henallux.dondesang.services.CollecteService;
import com.henallux.dondesang.services.ServiceBuilder;
import com.henallux.dondesang.task.LoadAddressesAsyncTask;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LocalisationFragment extends Fragment {
    private View view;
    private Button butCarte;
    private EditText clientAddress;
    private Switch sharePosition;
    private LocationManager locationManager;
    private LocationListener locationListenerGPS;
    double longitudeGPS, latitudeGPS;
    private String tag = "LOCATION_FRAGMENT";
    private FragmentManager fragmentManager;
    private LocationViewModel locationViewModel;
    private LoadAddressesAsyncTask loadAddressesAsyncTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getFragmentManager();

        //Récupération la localisation
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        locationListenerGPS = new LocationListener()
        {
            @Override
            public void onLocationChanged(final Location location)
            {
                latitudeGPS = location.getLatitude();
                longitudeGPS = location.getLongitude();
                Log.d(tag, "onLocatedChanged Lat : " + latitudeGPS + " long : " + longitudeGPS);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
                /*Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);*/

            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_localisation, container, false);
        locationViewModel = ViewModelProviders.of(getActivity()).get(LocationViewModel.class);

        clientAddress = (EditText) view.findViewById(R.id.editText_CodePostale);

        sharePosition = (Switch) view.findViewById(R.id.switch_sharePosition);

        sharePosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharePosition.isChecked())
                {
                    clientAddress.setEnabled(false);
                    toggleGPSUpdates();
                    Log.d(tag, "onClick Switch Latitude : " + latitudeGPS + " Longitude : " + longitudeGPS);

                }
                else
                {
                    clientAddress.setEnabled(true);
                    //locationManager.removeUpdates(locationListenerGPS);
                }
            }
        });

        butCarte = (Button) view.findViewById(R.id.but_RechercheCentre);

        //Récupération des collectes depuis l'API
        CollecteService collecteService = ServiceBuilder.buildService(CollecteService.class);
        Call<List<Collecte>> listCall = collecteService.getCollectes();
        listCall.enqueue(new Callback<List<Collecte>>() {
            @Override
            public void onResponse(Call<List<Collecte>> call, Response<List<Collecte>> response) {
                if (!response.isSuccessful()) {
                    butCarte.setEnabled(false);
                    Toast.makeText(getContext(), Constants.MSG_ERREUR_CHARGEMENT, Toast.LENGTH_LONG).show();
                    Log.d(Constants.TAG_GENERAL, "Code : " + response.code());
                    return;
                }

                if (response.body().isEmpty()) {
                    butCarte.setEnabled(false);
                }
                else
                {
                    locationViewModel.setCollectes(response.body());
                    butCarte.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<List<Collecte>> call, Throwable t) {
                butCarte.setEnabled(false);
                Toast.makeText(getContext(), Constants.MSG_ERREUR_CHARGEMENT, Toast.LENGTH_SHORT).show();
                Log.d(Constants.TAG_GENERAL, "Requête : " + call.request());
            }
        });

        butCarte.setEnabled(false);

        butCarte.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)

            {
                if (sharePosition.isChecked()) {
                    locationViewModel.setLocation(new LatLng(latitudeGPS, longitudeGPS));
                    ((LocationViewModel) locationViewModel).setUtiliseAddresse(false);

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.fragment_container,new CarteFragment(),"replaceFragmentByCarteFragment");
                    transaction.addToBackStack("LocalisationFragment");
                    transaction.commit();
                }
                else
                {
                    ((LocationViewModel) locationViewModel).setUtiliseAddresse(true);
                    if (Util.verificationCodePostal(clientAddress)) {
                        loadAddressesAsyncTask = new LoadAddressesAsyncTask(fragmentManager, getActivity());
                        loadAddressesAsyncTask.execute(clientAddress.getText().toString());
                    }
                }
        }
    }
        );

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        //Sauvegarder ici les données au cas ou l'utilisateur quitte subitement le fragement
    }

    private boolean checkLocation() {
        if (!isLocationEnabled())
        {
            showAlert();
            sharePosition.setChecked(false);
        }
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(R.string.activation_location)
                .setMessage(R.string.activiation_location_details)
                .setPositiveButton(R.string.activation_location_parametre, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                        sharePosition.setChecked(false);
                    }
                })
                .setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        sharePosition.setChecked(false);
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {

        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void toggleGPSUpdates() {
        if (!checkLocation())
            return;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED)
        {
            String tabPermission[] = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(getActivity(), tabPermission, Constants.PERMISSION_LOCATION);
            sharePosition.setChecked(false);
        }
        else
        {
            locationGPS();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSION_LOCATION: {
                if (hasAllPermissionsGranted(grantResults)) {
                    locationGPS();
                }
                else
                {
                    sharePosition.setChecked(false);
                }
            }
        }
    }

    public boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @SuppressLint("MissingPermission")
    public void locationGPS() {
        //Même en mettant des valeurs nulles, la position prends de 1 à 2 secondes pour être récupéré
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerGPS);

        //On s'assure donc de récupéré directement la position la plus récente afin de ne pas faire planter l'appli
        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (lastLocation != null) {
            longitudeGPS = lastLocation.getLongitude();
            latitudeGPS = lastLocation.getLatitude();
        }
        else
        {
            sharePosition.setChecked(false);
        }

        Log.d(tag, "Network provider started running");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loadAddressesAsyncTask != null) {
            loadAddressesAsyncTask.cancel(true);
        }
    }
}