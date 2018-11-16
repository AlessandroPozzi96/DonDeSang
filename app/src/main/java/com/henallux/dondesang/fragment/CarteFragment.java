package com.henallux.dondesang.fragment;

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
import com.henallux.dondesang.activity.MainActivity;
import com.henallux.dondesang.model.Application;
import com.henallux.dondesang.model.LocationViewModel;


public class CarteFragment extends Fragment {
    private View view;
    private Button butCarte;
    private Application applicationObject;
    private EditText codePostale;
    private Switch sharePosition;
    private ViewModel viewModel;
    private final int PERMISSION_LOCATION_GPS = 2;
    private LocationManager locationManager;
    private LocationListener locationListenerGPS;
    double longitudeGPS, latitudeGPS;
    private String tag = "LocationOnePlusOne";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_carte, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(LocationViewModel.class);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Récupération de la valeur du code postale
        codePostale = (EditText) view.findViewById(R.id.editText_CodePostale);

        //Lancement de la recherche de collecte lorsqu'on a une position valide
        butCarte = (Button) getView().findViewById(R.id.but_RechercheCentre);
        butCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        locationListenerGPS = new LocationListener()
        {
            public void onLocationChanged(Location location)
            {
                latitudeGPS = location.getLatitude();
                longitudeGPS = location.getLongitude();
                Log.d(tag, "onLocatedChanged Lat : " + latitudeGPS + " long : " + longitudeGPS);
                getActivity().runOnUiThread(new Runnable()
                {
                    @Override
                    public void run() {
                        //On peut afficher ici les coordonnées en temps réel
                        Log.d(tag, "run !");
                    }
                });
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
            }
        };
        toggleGPSUpdates();
        sharePosition = (Switch) getView().findViewById(R.id.switch_sharePosition);
        //On s'assure une première fois qu'on a bien les permissions

        sharePosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharePosition.isChecked())
                {
                    codePostale.setEnabled(false);

                    Log.d(tag, "onClick Switch Latitude : " + latitudeGPS + " Longitude : " + longitudeGPS);
                }
                else
                    {
                    codePostale.setEnabled(true);
                    locationManager.removeUpdates(locationListenerGPS);
                }
            }
        });

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
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void toggleGPSUpdates() {
        if (!checkLocation())
            return;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED)
        {
            String tabPermission[] = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            ActivityCompat.requestPermissions(getActivity(), tabPermission, PERMISSION_LOCATION_GPS);
        }
        else
        {
            locationGPS();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_LOCATION_GPS: {
                if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    locationGPS();
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    public void locationGPS() {

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60 * 1000, 10, locationListenerGPS);

        Log.d(tag, "GPS provider started running");
    }
}