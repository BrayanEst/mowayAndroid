package com.example.moway_rutas.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moway_rutas.Mapaagrande;
import com.example.moway_rutas.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Fragmentomapa extends Fragment {

    public Location location;
    public LocationManager locationManager;
    public Marker marker;
    public GoogleMap mMap;
    public double latitud = 0.0;
    public double longitud = 0.0;

    //VARIABLE PARA GUARDAR ACEES DEL PERMISO
    int requestcod = 200;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {

            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            googleMap.setMyLocationEnabled(true);
           // Mibicacion();
        }

        private void Mibicacion() {
            if (ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)  != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            actualizarUbicacion(location);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,locationListener);
        }
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                actualizarUbicacion(location);
            }
        };
        private void actualizarUbicacion(Location location) {
            if (location != null) {
                latitud = location.getLatitude();
                longitud = location.getLongitude();
                agregarMarcador(latitud, longitud);
            }
        }
        private void agregarMarcador(double latitud, double longitud) {
            LatLng coordenada = new LatLng(latitud, longitud);
            CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenada,16);
            if (marker != null) marker.remove();
            marker = mMap.addMarker(new MarkerOptions()
                    .position(coordenada)
                    .title("Mi ubicacion Actual")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.persona)));
            mMap.animateCamera(miUbicacion);
        }

    };

    public static Fragmentomapa newInstance() {
        
        Bundle args = new Bundle();
        
        Fragmentomapa fragment = new Fragmentomapa();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vista =  inflater.inflate(R.layout.fragment_fragmentomapa, container, false);
            verificarpermisos();
        return vista;
    }

    private void verificarpermisos() {
       int permiisoLocation =  ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION);
       int permiisoLocation2 =  ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_COARSE_LOCATION);
       int permisoConection =  ContextCompat.checkSelfPermission(getContext(),Manifest.permission.INTERNET);

        if (permiisoLocation ==  PackageManager.PERMISSION_GRANTED && permiisoLocation2 == PackageManager.PERMISSION_GRANTED && permisoConection == PackageManager.PERMISSION_GRANTED){
           // Toast.makeText(getContext(), "PERMISO LOCATIN E INTET READY", Toast.LENGTH_SHORT).show();
        }else{
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET
            },requestcod);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}