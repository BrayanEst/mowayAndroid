package com.example.moway_rutas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moway_rutas.Fragments.Chat.adpyertschats.Adapterchats;
import com.example.moway_rutas.Fragments.Chat.modelos.Chats;
import com.example.moway_rutas.Fragments.Chat.modelos.Estado;
import com.firebase.ui.auth.data.remote.GenericIdpAnonymousUpgradeLinkingHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class EntyActivity extends AppCompatActivity {

    CircleImageView img_user;
    TextView username;
    ImageView ic_conectado , ic_desconectado;
    SharedPreferences mpref;

    EditText et_mensaje_txt ;
    ImageButton btnEnviarMensaje;



    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref_estado = database.getReference("Estado").child(user.getUid());

    DatabaseReference ref_chat = database.getReference("Chat");

    //ID CHAT GLOBAL
    String id_chat_global;
    
    Boolean amigo_online = false;

    //recycler view ...
    RecyclerView rv_chats;
    Adapterchats adapter;
    ArrayList<Chats>chatlist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enty);

        //HABILITANDO ACTION BAR
        Toolbar toolbar = findViewById(R.id.tollbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mpref = getApplicationContext().getSharedPreferences("usuario_sp",MODE_PRIVATE);




        //referencer
        img_user = findViewById(R.id.imguserentyactivity);
        username = findViewById(R.id.textonameuser5);
        ic_conectado = findViewById(R.id.icon_conectadoActivity);
        ic_desconectado = findViewById(R.id.icon_desconectadoActivity);

        //valores qpor recivir GG XD

        String usuario = getIntent().getExtras().getString("nombre");
        String foto = getIntent().getExtras().getString("img_user");
        String id_user = getIntent().getExtras().getString("id_user");
        id_chat_global = getIntent().getExtras().getString("id_unico");

        colocarenvisto();

        et_mensaje_txt = findViewById(R.id.editTextTxtMensaje);
        btnEnviarMensaje = findViewById(R.id.btn_enviar_mensaje);
        btnEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msj = et_mensaje_txt.getText().toString();

                if (!msj.isEmpty()){

                    final Calendar c = Calendar.getInstance();
                    final SimpleDateFormat  timeformat = new SimpleDateFormat("HH:mm");
                    final SimpleDateFormat  dateformat = new SimpleDateFormat("DD/MM/yyyy");
                    String idpush = ref_chat.push().getKey();

                    if (amigo_online){
                        //mi constructor
                        Chats chatmensaje = new Chats(idpush,user.getUid(),id_user,msj,"si",dateformat.format(c.getTime()),timeformat.format(c.getTime()));
                        ref_chat.child(id_chat_global).child(idpush).setValue(chatmensaje);
                        Toast.makeText(EntyActivity.this, "mensaje enviado", Toast.LENGTH_SHORT).show();
                        et_mensaje_txt.setText("");
                    }else {
                        //mi constructor
                        Chats chatmensaje = new Chats(idpush,user.getUid(),id_user,msj,"no",dateformat.format(c.getTime()),timeformat.format(c.getTime()));
                        ref_chat.child(id_chat_global).child(idpush).setValue(chatmensaje);
                        Toast.makeText(EntyActivity.this, "mensaje enviado", Toast.LENGTH_SHORT).show();
                        et_mensaje_txt.setText("");
                    }
                }else{
                    Toast.makeText(EntyActivity.this, "No puedes enviar un mensaje basio", Toast.LENGTH_SHORT).show();
                }



            }
        });

        username.setText(usuario);
        Glide.with(this).load(foto).into(img_user);

//coloco el estado del chat
        final String id_user_sp = mpref.getString("usuario_sp","");

        final DatabaseReference ref = database.getReference("Estado").child(id_user_sp).child("chatcon");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String chatcon = snapshot.getValue(String.class);
                if (chatcon.equals(user.getUid())){
                    amigo_online = true;
                    ic_conectado.setVisibility(View.VISIBLE);
                    ic_desconectado.setVisibility(View.GONE);
                }else{
                    amigo_online = false;
                   // Toast.makeText(EntyActivity.this, "no esta conectado crak", Toast.LENGTH_SHORT).show();
                    ic_desconectado.setVisibility(View.VISIBLE);
                    ic_conectado.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //RV---
        rv_chats = findViewById(R.id.rvchat);
        rv_chats.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        rv_chats.setLayoutManager(linearLayoutManager);

        chatlist = new ArrayList<>();
        adapter = new Adapterchats(chatlist,this);
        rv_chats.setAdapter(adapter);

        leerMensajes();

    }//fin del oncreate

    private void colocarenvisto() {
        ref_chat.child(id_chat_global).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :snapshot.getChildren()){

                    Chats chats = dataSnapshot.getValue(Chats.class);
                   if (chats.getRecibe().equals(user.getUid())){
                       ref_chat.child(id_chat_global).child(chats.getId()).child("visto").setValue("si");
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void leerMensajes() {
        ref_chat.child(id_chat_global).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    chatlist.removeAll(chatlist);
                    for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                        Chats chats = dataSnapshot.getValue(Chats.class);
                        chatlist.add(chats);
                    }
                    adapter.notifyDataSetChanged();
                    setScroll();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setScroll() {
        rv_chats.scrollToPosition(adapter.getItemCount()-1);
    }


    @Override
    public void onResume() {
        super.onResume();
        estadoUsuario("conectado");
    }

    @Override
    public void onPause() {
        super.onPause();
        estadoUsuario("Desconectado");
        dameultimafecha();
    }

    private void dameultimafecha() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");

        ref_estado.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ref_estado.child("fecha").setValue(dateFormat.format(c.getTime()));
                ref_estado.child("hora").setValue(timeFormat.format(c.getTime()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void estadoUsuario(String estado) {

        ref_estado.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String id_user_sp = mpref.getString("usuario_sp","");

                Estado est = new Estado(estado , "","",id_user_sp);
                ref_estado.setValue(est);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}