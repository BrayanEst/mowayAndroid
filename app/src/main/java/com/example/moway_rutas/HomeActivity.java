package com.example.moway_rutas;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private static final int VALUE_UBI = 200;
    LocationManager locationManager;
    Location location;
    FloatingActionButton floatingActionButton;
    TextView Latitud, Longitud, txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        referenciar();
        verificarPermiso();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void verificarPermiso() {
        int PermisoUbi = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (PermisoUbi == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, VALUE_UBI);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case VALUE_UBI: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    referenciar();
                } else {
                    Toast.makeText(this, "De permiso sapa", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void referenciar() {
        floatingActionButton = findViewById(R.id.enviarcordenadas);
        Latitud = findViewById(R.id.Latitud);
        Longitud = findViewById(R.id.Longitud);
        txt = findViewById(R.id.txttxttxtxtxx);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Latitud.setText("" + String.valueOf(location.getLatitude()));
                Longitud.setText("" + String.valueOf(location.getLongitude()));
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> direccion = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
                    /*System.out.println(direccion.get(0).getAddressLine(0 ));*/
                    txt.setText(direccion.get(0).getAddressLine(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                //startActivity(intent);
            }
        });
    }

}