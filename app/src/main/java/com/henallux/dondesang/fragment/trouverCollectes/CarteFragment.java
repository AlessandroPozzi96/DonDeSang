package com.henallux.dondesang.fragment.trouverCollectes;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.henallux.dondesang.R;
import com.henallux.dondesang.model.LocationViewModel;

public class CarteFragment extends Fragment implements OnMapReadyCallback {
    private LocationViewModel locationViewModel;
    private GoogleMap mGoogleMap;
    private MapView mapView;
    private View view;

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
        //Normalement on va devoir boucler sur toutes les coordonnées reçu pour créer tous les markers
        googleMap
                .addMarker(new MarkerOptions()
                        .position(new LatLng(50.200323, 4.897113))
                        .title("Collecte de Falmagne")
                        .snippet("Horaires de cette collecte : Lundi : 10H00-15H00 Mardi ..."));
        //Ici on définit la position de l'utilisateur, obtenue soit via le GPS soit via le code postal
        LatLng userLtLng;
        if (locationViewModel.isUtiliseCodePostal()) {
            //Normalement l'API nous retourne le nom de la localité + ses coordonnées
            userLtLng = new LatLng(locationViewModel.getLocalite().getLocation().getLatitude(), locationViewModel.getLocalite().getLocation().getLongitude());
        }
        else
        {
            //Position déterminée dans le fragment LocalisationFragment
            userLtLng = new LatLng(locationViewModel.getLocation().getLatitude(), locationViewModel.getLocation().getLongitude());
        }
        CameraPosition userPosition = CameraPosition
                .builder()
                .target(userLtLng)
                .zoom(11)
                .tilt(35)
                .build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(userPosition));
    }
}
