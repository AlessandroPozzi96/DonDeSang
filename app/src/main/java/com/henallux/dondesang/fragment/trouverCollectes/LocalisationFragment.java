package com.henallux.dondesang.fragment.trouverCollectes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
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

import com.henallux.dondesang.R;
import com.henallux.dondesang.exception.ModelException;
import com.henallux.dondesang.fragment.fragmentLogin.RegisterFragment;
import com.henallux.dondesang.model.Application;
import com.henallux.dondesang.model.LocationViewModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class LocalisationFragment extends Fragment {
    private View view;
    private Button butCarte;
    private Application applicationObject;
    private EditText codePostale;
    private Switch sharePosition;
    private final int PERMISSION_LOCATION_GPS = 2;
    private LocationManager locationManager;
    private LocationListener locationListenerGPS;
    double longitudeGPS, latitudeGPS;
    private String tag = "LocationOnePlusOne";
    private FragmentManager fragmentManager;
    private LocationViewModel locationViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_localisation, container, false);
        locationViewModel = ViewModelProviders.of(getActivity()).get(LocationViewModel.class);

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
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        toggleGPSUpdates();
        //locationManager.removeUpdates(locationListenerGPS);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        //Récupération de la valeur du code postale
        codePostale = (EditText) view.findViewById(R.id.editText_CodePostale);

        sharePosition = (Switch) getView().findViewById(R.id.switch_sharePosition);

        sharePosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharePosition.isChecked())
                {
                    codePostale.setEnabled(false);
                    Toast.makeText(getActivity(), "onClick Switch Latitude : " + latitudeGPS + " Longitude : " + longitudeGPS, Toast.LENGTH_SHORT).show();
                    Log.d(tag, "onClick Switch Latitude : " + latitudeGPS + " Longitude : " + longitudeGPS);

                }
                else
                    {
                    codePostale.setEnabled(true);
                }
            }
        });

        //Lancement de la recherche de collecte lorsqu'on a une position valide
        butCarte = (Button) getView().findViewById(R.id.but_RechercheCentre);
        butCarte.setOnClickListener(new View.OnClickListener()
                                    {
    @Override
    public void onClick(View v)
    {
        //En fonction du choix de l'utilisateur on envoie le code postal ou les coordonnées
        if (sharePosition.isChecked()) {
            locationViewModel.setLocation(new com.henallux.dondesang.model.Location(longitudeGPS, latitudeGPS));

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container,new CarteFragment(),"replaceFragmentByCarteFragment");
            transaction.commit();
        }
        else
        {
            try
            {
                locationViewModel.setCodePostal(codePostale.getText().toString());
            } catch (ModelException e)
            {
                Toast.makeText(getActivity(), "Le code postal doit être égal a 4, veuillez réessayer !", Toast.LENGTH_SHORT).show();
                codePostale.setText("");
            }
        }
    }
}
        );

    }

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
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
            ActivityCompat.requestPermissions(getActivity(), tabPermission, 1);
        }
        else
        {
            locationGPS();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(hasAllPermissionsGranted(grantResults)){
            locationGPS();
        }else {
            // some permission are denied.
            showAlert();
            Log.d(tag, "Une ou plusieurs permission are denied");
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
        //la position est directement mise à jour en mettant des petites valeurs
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerGPS);
        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        longitudeGPS = lastLocation.getLongitude();
        latitudeGPS = lastLocation.getLatitude();

        Log.d(tag, "Network provider started running");
    }
}