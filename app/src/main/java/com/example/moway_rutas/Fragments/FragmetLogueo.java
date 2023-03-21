package com.example.moway_rutas.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moway_rutas.LoginChat;
import com.example.moway_rutas.R;
import com.example.moway_rutas.Reportes;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class FragmetLogueo extends Fragment {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mauthStateListener;
    public static  final int SING_IN = 1;
    //lista para colocar los email
    List<AuthUI.IdpConfig> provaiders  = Arrays.asList(
            new AuthUI.IdpConfig.GoogleBuilder().build()
    );

    public FragmetLogueo() {
        // Required empty public constructor
    }

    public static FragmetLogueo newInstance() {

        Bundle args = new Bundle();

        FragmetLogueo fragment = new FragmetLogueo();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_fragmet_logueo, container, false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mauthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    goHome();
                }else {
                    Toast.makeText(getContext(), "Ingresa un email por favor", Toast.LENGTH_SHORT).show();

                    startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(provaiders)
                            .setIsSmartLockEnabled(false)
                            .build(),SING_IN);
                }
            }
        };

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mauthStateListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mauthStateListener);
    }

    private void goHome() {
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