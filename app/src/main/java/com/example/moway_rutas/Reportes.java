package com.example.moway_rutas;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.moway_rutas.Fragments.Fragmentomapa;
import com.example.moway_rutas.Fragments.FrangmentEmpresa;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class Reportes extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);

        referenciar();
        initValues();
        menuNavBotton();
    }
    private void referenciar(){
        bottomNavigationView = findViewById(R.id.menuNavFragmetChat);
    }

    private void initValues() {
        fragmentManager = getSupportFragmentManager();
       // loadFirstFragment();
    }

    //metodo para llamar a los fragmentos en mi actividad
    private void menuNavBotton(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mapa:
                       // Intent intent2 = new Intent(Reportes.this,MainFragments.class);
                        fragment = Fragmentomapa.newInstance();
                        openFragments(fragment);
                        //startActivity(intent2);

                        break;
                    case R.id.empresa:
                        //Intent intent3 = new Intent(Reportes.this,MainFragments.class);
                        fragment = FrangmentEmpresa.newInstance();
                        openFragments(fragment);
                        //startActivity(intent3);

                        break;
                    case R.id.reportes:
                        Intent intent = new Intent(Reportes.this,Reportes.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }
    private void openFragments(Fragment fragment){
        fragmentManager.beginTransaction()
                .replace(R.id.MainFragmtChat ,fragment)
                .commit();
    }
    private void loadFirstFragment(){
        fragment = Fragmentomapa.newInstance();
        openFragments(fragment);
    }

}