package com.henallux.dondesang.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.henallux.dondesang.R;
import com.henallux.dondesang.model.Application;
import com.henallux.dondesang.activity.CarteActivity;

public class CarteFragment extends Fragment implements LocationListener {
    private View view;
    private Button butCarte;
    private Application applicationObject;
    private EditText codePostale;
    private Switch sharePosition;
    private LocationManager locationManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_carte, container, false);

        //Récupération de la valeur du code postale
        codePostale = (EditText) view.findViewById(R.id.editText_CodePostale);

        //Lancement de la recherche de collecte lorsqu'on a une position valide
        butCarte = (Button) view.findViewById(R.id.but_RechercheCentre);
        butCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                applicationObject = (Application) getActivity().getApplicationContext();
                applicationObject.setCodePostale(codePostale.getText().toString());

                Intent intent = new Intent(getActivity(), CarteActivity.class);
                startActivity(intent);
            }
        });

        //Récupération de la position de l'utilisateur + désactivation de l'editText

        locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        sharePosition = (Switch) view.findViewById(R.id.switch_sharePosition);
        sharePosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharePosition.isChecked()) {
                    codePostale.setEnabled(false);
                    //On demande la permission a l'utilisateur

                } else
                    {
                    codePostale.setEnabled(true);
                }
            }
        });

        if (Build.VERSION.SDK_INT < 23) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, CarteFragment.this);
        }
        else
        {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            else
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, CarteFragment.this);
            }
        }

        return view;
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, CarteFragment.this);
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getActivity(), "Latitude : " + location.getLatitude() + " \n longitiude : " + location.getLongitude(), Toast.LENGTH_SHORT).show();
        Log.d("LocationOnePlusOne", location.toString());
        getApplicationObject().setLatitude(location.getLatitude());
        getApplicationObject().setLongitude(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent iAskLocalisation = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        iAskLocalisation.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(iAskLocalisation);
    }

    public Application getApplicationObject() {
        return applicationObject;
    }

    public void setApplicationObject(Application applicationObject) {
        this.applicationObject = applicationObject;
    }
}