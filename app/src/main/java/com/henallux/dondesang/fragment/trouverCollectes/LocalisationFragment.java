package com.henallux.dondesang.fragment.trouverCollectes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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

import com.henallux.dondesang.Constants;
import com.henallux.dondesang.R;
import com.henallux.dondesang.Util;
import com.henallux.dondesang.exception.ModelException;
import com.henallux.dondesang.model.LocationViewModel;
import com.henallux.dondesang.task.LoadLocalitiesAsyncTask;


public class LocalisationFragment extends Fragment {
    private View view;
    private Button butCarte;
    private EditText codePostale;
    private Switch sharePosition;
    private LocationManager locationManager;
    private LocationListener locationListenerGPS;
    double longitudeGPS, latitudeGPS;
    private String tag = "LOCATION_FRAGMENT";
    private FragmentManager fragmentManager;
    private LocationViewModel locationViewModel;
    private LoadLocalitiesAsyncTask loadLocalitiesAsyncTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getFragmentManager();

        //Récupération la localisation
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //On initialise la variable au cas ou

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
                startActivity(intent);
                */
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_localisation, container, false);
        locationViewModel = ViewModelProviders.of(getActivity()).get(LocationViewModel.class);

        //Récupération de la valeur du code postale
        codePostale = (EditText) view.findViewById(R.id.editText_CodePostale);

        sharePosition = (Switch) view.findViewById(R.id.switch_sharePosition);

        sharePosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharePosition.isChecked())
                {
                    toggleGPSUpdates();
                    codePostale.setEnabled(false);
                    Toast.makeText(getActivity(), "onClick Switch Latitude : " + latitudeGPS + " Longitude : " + longitudeGPS, Toast.LENGTH_SHORT).show();
                    Log.d(tag, "onClick Switch Latitude : " + latitudeGPS + " Longitude : " + longitudeGPS);

                }
                else
                {
                    codePostale.setEnabled(true);
                    //locationManager.removeUpdates(locationListenerGPS);
                }
            }
        });

        //Lancement de la recherche de collecte lorsqu'on a une position valide
        butCarte = (Button) view.findViewById(R.id.but_RechercheCentre);
        butCarte.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)

            {
            //En fonction du choix de l'utilisateur on envoie le code postal ou les coordonnées
            if (sharePosition.isChecked()) {
                locationViewModel.setLocation(new com.henallux.dondesang.model.Location(longitudeGPS, latitudeGPS));
                ((LocationViewModel) locationViewModel).setUtiliseCodePostal(false);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container,new CarteFragment(),"replaceFragmentByCarteFragment");
                transaction.commit();
            }
            else
            {
                ((LocationViewModel) locationViewModel).setUtiliseCodePostal(true);
                if (Util.verificationCodePostal(codePostale)) {
                    try
                    {
                        locationViewModel.setCodePostal(codePostale.getText().toString());
                    } catch (ModelException e)
                    {
                        Toast.makeText(getActivity(), "Impossible de récupérer le code postale !", Toast.LENGTH_SHORT).show();
                        codePostale.setText("");
                    }
                    finally {
                        loadLocalitiesAsyncTask = new LoadLocalitiesAsyncTask(fragmentManager, getActivity());
                        loadLocalitiesAsyncTask.execute(codePostale.getText().toString());
                    }
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
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Activer la localisation")
                .setMessage("Activer les services de localisation 'Haute précision' ou 'Economie d\'énergie' pour utiliser cette application \n (Le GPS seul ne suffit pas)")
                .setPositiveButton("Paramètre de localisation", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                        sharePosition.setChecked(false);
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
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
            ActivityCompat.requestPermissions(getActivity(), tabPermission, Constants.PERMISSION_LOCATION_NETWORK);
        }
        else
        {
            locationGPS();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSION_LOCATION_NETWORK: {
                if (hasAllPermissionsGranted(grantResults)) {
                    locationGPS();
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
            //Si le téléphone ne récupère pas les coordonnées à temps on mets la position de la Belgique (Solution temporaire !)
            longitudeGPS = 4.469936;
            latitudeGPS = 50.503887;
        }

        Log.d(tag, "Network provider started running");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loadLocalitiesAsyncTask != null) {
            loadLocalitiesAsyncTask.cancel(true);
        }
    }
}