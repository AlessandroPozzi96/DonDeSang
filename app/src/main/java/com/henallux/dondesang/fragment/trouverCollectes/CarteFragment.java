package com.henallux.dondesang.fragment.trouverCollectes;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.henallux.dondesang.R;
import com.henallux.dondesang.Util;
import com.henallux.dondesang.model.Collecte;
import com.henallux.dondesang.model.Jourouverture;
import com.henallux.dondesang.model.LocationViewModel;

import java.util.ArrayList;
import java.util.List;

public class CarteFragment extends Fragment implements OnMapReadyCallback {
    private LocationViewModel locationViewModel;
    private GoogleMap mGoogleMap;
    private MapView mapView;
    private View view;
    private String tag = "CarteFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_carte, container, false);
        locationViewModel = ViewModelProviders.of(getActivity()).get(LocationViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) view.findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        String telephone;
        String horaires;

        if (locationViewModel.getCollectes()!= null && locationViewModel.getCollectes().size() > 0) {
            List<Collecte> collectes = locationViewModel.getCollectes();
            for (Collecte collecte : collectes) {
                telephone = (collecte.getTelephone() == null)? " " + getResources().getString(R.string.pas_de_numero) : " " + getResources().getString(R.string.tel) + "0" + collecte.getTelephone();
                horaires = "";
                for (Jourouverture jourouverture : collecte.getJourouverture()) {
                    if (jourouverture.getDate() != null) {
                        horaires += jourouverture.dateFormate();
                    }
                    else
                    {
                        horaires += Util.getJourSemaine(jourouverture.getJour(), getContext());
                    }
                    horaires += getResources().getString(R.string.de) + " " + jourouverture.getHeureDebut() + " " + getResources().getString(R.string.a) + " " + jourouverture.getHeureFin() + "\n";
                }

                googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(collecte.getLatitude(), collecte.getLongitude()))
                .title(collecte.getNom())
                .snippet(horaires + "\n" + telephone));
            }
        }
        else
        {
            Toast.makeText(getActivity(), R.string.collectes_indisponible, Toast.LENGTH_LONG).show();
            Log.d(tag, "Pas de collectes disponibles !");
        }
        
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                //layout of your info window
                LinearLayout info = new LinearLayout(getContext());
                info.setOrientation(LinearLayout.VERTICAL);
                //for displaying title
                TextView title = new TextView(getContext());
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());
                //for displaying snippet
                TextView snippet = new TextView(getContext());
                snippet.setTextColor(Color.BLACK);
                snippet.setText(marker.getSnippet());
                snippet.setTypeface(null, Typeface.ITALIC);
                snippet.setGravity(Gravity.LEFT);
                snippet.setCursorVisible(true);

                info.addView(title); //Adding title in your custom window
                info.addView(snippet); //adding description

                return info;
            }
        });

        //Ici on définit la position de l'utilisateur, obtenue soit via le GPS soit via le code postal
        LatLng userLtLng;
        if (locationViewModel.isUtiliseAddresse()) {
            //Normalement l'API nous retourne le nom de la localité + ses coordonnées
            userLtLng = new LatLng(locationViewModel.getAddress().getLatitude(), locationViewModel.getAddress().getLongitude());
        }
        else
        {
            //Position déterminée dans le fragment LocalisationFragment
            userLtLng = new LatLng(locationViewModel.getLocation().latitude, locationViewModel.getLocation().longitude);
        }
        CameraPosition userPosition = CameraPosition
                .builder()
                .target(userLtLng)
                .zoom(12)
                .tilt(35)
                .build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(userPosition));
    }
}
