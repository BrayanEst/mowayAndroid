package com.example.moway_rutas.Fragments.Chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.moway_rutas.Fragments.Chat.adpyertschats.AdapterChatLista;
import com.example.moway_rutas.Fragments.Chat.adpyertschats.AdapterUsuarios;
import com.example.moway_rutas.Fragments.Chat.modelos.UsersChat;
import com.example.moway_rutas.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentChatporpersona extends Fragment {


    public FragmentChatporpersona() {
        // Required empty public constructor
    }

    public static FragmentChatporpersona newInstance() {

        Bundle args = new Bundle();

        FragmentChatporpersona fragment = new FragmentChatporpersona();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        View  view  = inflater.inflate(R.layout.fragment_chatporpersona, container, false);

        //metodos
        //refencer(view);
        ProgressBar progressBar;
        progressBar = view.findViewById(R.id.prgresbarUser);

        //recycler view
        RecyclerView rv ;
        ArrayList<UsersChat> userArraylist ;
        AdapterChatLista adapter;
        LinearLayoutManager mLayaouManager;

        mLayaouManager = new LinearLayoutManager(getContext());
        mLayaouManager.setReverseLayout(true);
        mLayaouManager.setStackFromEnd(true);
        rv = view.findViewById(R.id.recyclerUsersChat);
        rv.setLayoutManager(mLayaouManager);

        userArraylist = new ArrayList<>();
        adapter = new AdapterChatLista(userArraylist,getContext());
        rv.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("Users");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    rv.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    userArraylist.removeAll(userArraylist);
                    for (DataSnapshot snap: snapshot.getChildren()) {
                        UsersChat usersChat = snap.getValue(UsersChat.class);
                        userArraylist.add(usersChat);
                    }
                    //    Toast.makeText(getContext(), "wii", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "No existen usuarios", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //findelcreate del fragment
        return view;

    }
}