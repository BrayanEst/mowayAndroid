package com.example.moway_rutas.Fragments.Chat.adpyertschats;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moway_rutas.EntyActivity;
import com.example.moway_rutas.Fragments.Chat.FragmentUsersTheChat;
import com.example.moway_rutas.Fragments.Chat.modelos.Solicitudes;
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
import java.util.List;

public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.viewholderAdapters> {

     List<UsersChat> usersChatList;
     Context context;

     FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

     FirebaseDatabase database = FirebaseDatabase.getInstance();

     SharedPreferences mpref;

    public AdapterUsuarios(List<UsersChat> usersChatList, Context context) {
        this.usersChatList = usersChatList;
        this.context = context;

    }


    @NonNull
    @Override
    public viewholderAdapters onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_usuarios,parent,false);
        viewholderAdapters holderAdapters = new viewholderAdapters(view);
        return holderAdapters;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholderAdapters holder, int position) {

        final Vibrator vibrator =(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        UsersChat userrss = usersChatList.get(position);

        Glide.with(context)
                .load(userrss.getFoto())
                .into(holder.img_Users);

        holder.textousuario.setText(userrss.getNombre());

        //Ocultar el usuario si tiene nuesto musmo id //sino muestamelo
        if(userrss.getId().equals(user.getUid())){
            holder.cardView.setVisibility(View.GONE);
        }else {
            holder.cardView.setVisibility(View.VISIBLE);
        }

        DatabaseReference ref_mis_botones = database.getReference("Solicitudes").child(user.getUid());
        ref_mis_botones.child(userrss.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String estado = snapshot.child("estado").getValue(String.class);
                if (snapshot.exists()){
                    if (estado.equals("enviado")){
                        //muesto el boton de enciiado
                        holder.send.setVisibility(View.VISIBLE);
                        holder.add.setVisibility(View.GONE);
                        holder.amigos.setVisibility(View.GONE);
                        holder.tengosolisitud.setVisibility(View.GONE);

                       holder.progressBar.setVisibility(View.GONE);
                    }
                    if (estado.equals("amigos")){
                        //muesto el boton de enciiado
                        holder.send.setVisibility(View.GONE);
                        holder.add.setVisibility(View.GONE);
                        holder.amigos.setVisibility(View.VISIBLE);
                        holder.tengosolisitud.setVisibility(View.GONE);

                        holder.progressBar.setVisibility(View.GONE);
                    }

                    if (estado.equals("solicitud")){
                        //muesto el boton de enciiado
                        holder.send.setVisibility(View.GONE);
                        holder.add.setVisibility(View.GONE);
                        holder.amigos.setVisibility(View.GONE);
                        holder.tengosolisitud.setVisibility(View.VISIBLE);

                        holder.progressBar.setVisibility(View.GONE);
                    }

                    }else {
                    holder.send.setVisibility(View.GONE);
                    holder.add.setVisibility(View.VISIBLE);
                    holder.amigos.setVisibility(View.GONE);
                    holder.tengosolisitud.setVisibility(View.GONE);

                    holder.progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //AGRGO LOS BOTONES DEPENDIENDO EL CLIIK OBTENIIENDO BVALORES DE LA BASE DE DATOS COMO DEL MODELO
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               final DatabaseReference A = database.getReference("Solicitudes").child(user.getUid());
                A.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        Solicitudes sol = new Solicitudes("enviado","");
                            A.child(userrss.getId()).setValue(sol);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                final DatabaseReference B = database.getReference("Solicitudes").child(userrss.getId());
                B.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                            Solicitudes sol = new Solicitudes("solicitud","");
                            B.child(user.getUid()).setValue(sol);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

                DatabaseReference count = database.getReference("Contador").child(userrss.getId());
                        count.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    Integer val = snapshot.getValue(Integer.class);
                                    if (val == 0 ){
                                        count.setValue(1);
                                    }else {
                                        count.setValue(val + 1);
                                    }
                                }else{
                                    count.setValue(1);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        vibrator.vibrate(500);

            }//fin del onClick

        });

    holder.tengosolisitud.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            final String idChat = ref_mis_botones.push().getKey();

            final DatabaseReference A = database.getReference("Solicitudes").child(userrss.getId()).child(user.getUid());
            A.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    //paso datos por mi modelo adeas que le paso un id unico al chat
                    Solicitudes sol = new Solicitudes("amigos",idChat);
                    A.setValue(sol);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            final DatabaseReference B = database.getReference("Solicitudes").child(user.getUid()).child(userrss.getId());
            B.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    Solicitudes sol = new Solicitudes("amigos",idChat);
                    B.setValue(sol);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            vibrator.vibrate(500);
        }
    });

    holder.amigos.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mpref = v.getContext().getSharedPreferences("usuario_sp",Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = mpref.edit();

            final DatabaseReference ref = database.getReference("Solicitudes").child(user.getUid()).child(userrss.getId()).child("idchat");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String id_unico = snapshot.getValue(String.class);
                    if (snapshot.exists()){
                        Intent passs = new Intent(v.getContext(), EntyActivity.class);
                        passs.putExtra("nombre",userrss.getNombre());
                        passs.putExtra("img_user",userrss.getFoto());
                        passs.putExtra("id_user",userrss.getId());
                        passs.putExtra("id_unico",id_unico);

                        //almacenao mi variable share (las share se guardan en el tlefono)
                        editor.putString("usuario_sp",userrss.getId());
                        editor.apply();

                        v.getContext().startActivity(passs);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    });


    }//fin del onview holder

    @Override
    public int getItemCount() {
        return usersChatList.size();
    }

    public class viewholderAdapters extends RecyclerView.ViewHolder {

        TextView textousuario;
        ImageView img_Users;
        CardView cardView;
        Button add , send , amigos ,tengosolisitud;
        ProgressBar progressBar;

        public viewholderAdapters(@NonNull View itemView) {
            super(itemView);
            textousuario = itemView.findViewById(R.id.textonameuser1);
            img_Users = itemView.findViewById(R.id.imguserchat1);
            cardView = itemView.findViewById(R.id.cardviewRow_Users);
            //ubotones ocultos
            add = itemView.findViewById(R.id.btn_add);
            send = itemView.findViewById(R.id.btn_send);
            amigos = itemView.findViewById(R.id.btn_amigos);
            tengosolisitud = itemView.findViewById(R.id.tengosolisitud);
            progressBar = itemView.findViewById(R.id.progresBarRow);
        }
    }
}
