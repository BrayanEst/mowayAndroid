package com.example.moway_rutas.Fragments.Chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moway_rutas.Chat.AdapterMensajes;
import com.example.moway_rutas.Chat.Mensaje;
import com.example.moway_rutas.Fragments.Chat.modelos.Chats;
import com.example.moway_rutas.Fragments.Chat.modelos.UsersChat;
import com.example.moway_rutas.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;


public class FragmentChatGlobal extends Fragment {

    AdapterMensajes adapter;

    RecyclerView rvChatglobal;
    ImageButton button;
    EditText mensajes;
    TextView nombre ;

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseDatabase database ;
    DatabaseReference databaseReference;


    public FragmentChatGlobal() {
        // Required empty public constructor
    }

    public static FragmentChatGlobal newInstance() {

        Bundle args = new Bundle();

        FragmentChatGlobal fragment = new FragmentChatGlobal();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_chat_global, container, false);

         referecer(view);
         revfuncionalidad();
         basededatos();

        return view;
    }

    private void basededatos() {
        database= FirebaseDatabase.getInstance();
        //creo una carpeta en la cul voy a guardar los mensajes del chat global
        databaseReference = database.getReference("ChatGlobal");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Mensaje m = snapshot.getValue(Mensaje.class);
                adapter.addMensaje(m);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void revfuncionalidad() {
        adapter = new AdapterMensajes(getContext());
        LinearLayoutManager l = new LinearLayoutManager(getContext());
        rvChatglobal.setLayoutManager(l);
        rvChatglobal.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               databaseReference.push().setValue(new Mensaje(mensajes.getText().toString(),user.getDisplayName(),"","1",""));
                mensajes.setText("");
            }
        });

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                scrollbar();
            }
        });
    }

    private void scrollbar() {
        rvChatglobal.scrollToPosition(adapter.getItemCount()-1);
    }

    private void referecer(View view) {

        //CardVire page principal colocar las imagenes de mi cuenta


        TextView textoUser = view.findViewById(R.id.textonameuser2);
        CircleImageView imgmyperdil = view.findViewById(R.id.imguserchat2);

        assert user != null;
        textoUser.setText(user.getDisplayName());

        Glide.with(this)
                .load(user.getPhotoUrl())
                .into(imgmyperdil);
        //fin colocar las imagenes de mi cuenta
        rvChatglobal = view.findViewById(R.id.rvchatGlobal);
        mensajes = view.findViewById(R.id.ChatGlobalEditText);
        button =  view.findViewById(R.id.EnviarImgButton);

    }
}