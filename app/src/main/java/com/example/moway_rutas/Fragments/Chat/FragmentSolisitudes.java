package com.example.moway_rutas.Fragments.Chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moway_rutas.R;


public class FragmentSolisitudes extends Fragment {


    public FragmentSolisitudes() {
        // Required empty public constructor
    }

    public static FragmentSolisitudes newInstance() {

        Bundle args = new Bundle();

        FragmentSolisitudes fragment = new FragmentSolisitudes();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_solisitudes, container, false);

        return view;
    }
}