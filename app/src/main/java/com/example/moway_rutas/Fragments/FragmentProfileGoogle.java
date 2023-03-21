package com.example.moway_rutas.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moway_rutas.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentProfileGoogle extends Fragment {

    BottomNavigationView bottomNavigationView;

    ImageView viewProfile , iralchat;
    TextView textousuario,textogmail;

    public FragmentProfileGoogle() {

    }

    public static FragmentProfileGoogle newInstance() {

        Bundle args = new Bundle();

        FragmentProfileGoogle fragment = new FragmentProfileGoogle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View view = inflater.inflate(R.layout.fragment_profile_google, container, false);


        llamarvalores(view);

        logaut(view);
        return view;
    }

    private void llamarvalores(View v) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        viewProfile = v.findViewById(R.id.imageUserGoogle);
        textousuario = v.findViewById(R.id.textoNombreUsuario);
        textogmail = v.findViewById(R.id.textoGmailUsuaio);
        iralchat = v.findViewById(R.id.iralchat);

        Glide.with(getContext())
                .load(user.getPhotoUrl())
                .into(viewProfile);

        textousuario.setText(user.getDisplayName());
        textogmail.setText(user.getEmail());

        iralchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vamosalchat();
            }
        });
    }


    private void logaut(View v) {
        bottomNavigationView = v.findViewById(R.id.logant_fragment);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_cerrar:

                        AuthUI.getInstance().signOut(getContext())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        vamosalogin();
                                        Toast.makeText(getContext(), "sucefull", Toast.LENGTH_SHORT).show();
                                    }

                                    private void vamosalogin() {
                                        // Create new fragment and transaction
                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                                        transaction.setReorderingAllowed(true);
                                        // Reemplaza lo que esté en la vista de este fragmento con otro
                                        transaction.replace(R.id.MainFragmt, FragmetLogueo.newInstance());
                                        // Commit the transaction
                                        transaction.commit();
                                    }
                                });
                        break;
                }
                return true;
            }
        });
    }

    private void vamosalchat() {
        // Create new fragment and transaction
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        // Reemplaza lo que esté en la vista de este fragmento con otro
        transaction.replace(R.id.MainFragmt, FragmentChat.newInstance());
        // Commit the transaction
        transaction.commit();
    }
}