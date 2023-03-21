package com.example.moway_rutas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.moway_rutas.Fragments.FragmentChat;
import com.example.moway_rutas.Fragments.Fragmentomapa;
import com.example.moway_rutas.Fragments.FragmetLogueo;
import com.example.moway_rutas.Fragments.FrangmentEmpresa;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainFragments extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragments);



        referenciar();
        initValues();
        menu();
    }
    @Override
    public void onBackPressed(){
        Toast.makeText(this, "si das otro clik te vas a salir ", Toast.LENGTH_SHORT).show();
    }

    private void referenciar(){
        bottomNavigationView = findViewById(R.id.menuNavFragmet);
    }

    private void initValues() {
        fragmentManager = getSupportFragmentManager();
        loadFirstFragment();
    }


    private void openFragments(Fragment fragment){
        fragmentManager.beginTransaction()
                        .replace(R.id.MainFragmt ,fragment)
                        .commit();
    }

    private void loadFirstFragment(){
        fragment = Fragmentomapa.newInstance();
        openFragments(fragment);
    }

    //metodo para llamar a los fragmentos en mi actividad
    private void menu(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mapa:
                        fragment = Fragmentomapa.newInstance();
                        openFragments(fragment);
                        break;
                    case R.id.empresa:
                        fragment = FrangmentEmpresa.newInstance();
                        openFragments(fragment);
                        break;
                    case R.id.reportes:
                        fragment = FragmetLogueo.newInstance();
                        openFragments(fragment);
                        break;
                }
                return true;
            }
        });
    }
}