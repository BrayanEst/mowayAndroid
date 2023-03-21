package com.example.moway_rutas.Fragments.Chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moway_rutas.Fragments.Chat.adpyertschats.AdapterUsuarios;
import com.example.moway_rutas.Fragments.Chat.modelos.UsersChat;
import com.example.moway_rutas.Fragments.FragmentProfileGoogle;
import com.example.moway_rutas.Fragments.FragmentReturRutas;
import com.example.moway_rutas.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FragmentUsersTheChat extends Fragment {

    LinearLayout manager;


    public FragmentUsersTheChat() {
        // Required empty public constructor
    }

    public static FragmentUsersTheChat newInstance() {

        Bundle args = new Bundle();

        FragmentUsersTheChat fragment = new FragmentUsersTheChat();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_users_the_chat, container, false);

        //metodos
        refencer(view);
        ProgressBar progressBar;
        progressBar = view.findViewById(R.id.prgresbarUser);

        //recycler view
        RecyclerView rv ;
        ArrayList<UsersChat>userArraylist ;
        AdapterUsuarios  adapter;
        LinearLayoutManager mLayaouManager;

        mLayaouManager = new LinearLayoutManager(getContext());
        mLayaouManager.setReverseLayout(true);
        mLayaouManager.setStackFromEnd(true);
        rv = view.findViewById(R.id.recyclerUsersChat);
        rv.setLayoutManager(mLayaouManager);

        userArraylist = new ArrayList<>();
        adapter = new AdapterUsuarios(userArraylist,getContext());
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

        manager= view.findViewById(R.id.linearmuprofile);
        manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remplazofragmento();
            }
        });

        //findelcreate del fragment
        return view;
    }

    public void remplazofragmento() {
        // Create new fragment and transaction
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        // Reemplaza lo que esté en la vista de este fragmento con otro
        transaction.replace(R.id.MainFragmt, FragmentProfileGoogle.newInstance());
        // Commit the transaction
        transaction.commit();
    }


    private void refencer(View view) {
        //llamo a mi base de datos para obtener todos los datos del email

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        TextView textoUser = view.findViewById(R.id.textonameuser);
        ImageView img_user = view.findViewById(R.id.imguserchat);


        assert user != null;
        textoUser.setText(user.getDisplayName());

        Glide.with(this)
                .load(user.getPhotoUrl())
                .into(img_user);
    }
}