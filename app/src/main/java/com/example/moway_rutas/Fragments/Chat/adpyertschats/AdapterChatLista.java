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
import com.example.moway_rutas.Fragments.Chat.modelos.UsersChat;
import com.example.moway_rutas.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterChatLista extends RecyclerView.Adapter<AdapterChatLista.viewholderAdaptersChatList> {

     List<UsersChat> usersChatList;
     Context context;

     FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

     FirebaseDatabase database = FirebaseDatabase.getInstance();

    SharedPreferences mpref;

    public AdapterChatLista(List<UsersChat> usersChatList, Context context) {
        this.usersChatList = usersChatList;
        this.context = context;
    }


    @NonNull
    @Override
    public viewholderAdaptersChatList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chatlista,parent,false);
        viewholderAdaptersChatList holderAdapters = new viewholderAdaptersChatList(view);
        return holderAdapters;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholderAdaptersChatList holder, int position) {

        UsersChat userrss = usersChatList.get(position);

        final Vibrator vibrator =(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);

        holder.textousuario.setText(userrss.getNombre());
        Glide.with(context)
                .load(userrss.getFoto())
                .into(holder.img_Users);

        DatabaseReference ref_mis_solicitudes = database.getReference("Solicitudes").child(user.getUid());
        ref_mis_solicitudes.child(userrss.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String estado = snapshot.child("estado").getValue(String.class);

                if (snapshot.exists()){
                    if (estado.equals("amigos")){
                        holder.cardView.setVisibility(View.VISIBLE);
                    }else {
                        holder.cardView.setVisibility(View.GONE);
                    }
                }else{
                    holder.cardView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");

        DatabaseReference ref_estado = database.getReference("Estado").child(userrss.getId());

        ref_estado.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               String estado = snapshot.child("estado").getValue(String.class);
               String fecha = snapshot.child("fecha").getValue(String.class);
               String hora = snapshot.child("hora").getValue(String.class);

               if (snapshot.exists()){
                   if(estado.equals("conectado")){
                       holder.tv_conectado.setVisibility(View.VISIBLE);
                       holder.icon_conectado.setVisibility(View.VISIBLE);
                       holder.tv_desconectado.setVisibility(View.GONE);
                       holder.icon_desconectado.setVisibility(View.GONE);
                   }else {
                       holder.tv_desconectado.setVisibility(View.VISIBLE);
                       holder.icon_desconectado.setVisibility(View.VISIBLE);
                       holder.tv_conectado.setVisibility(View.GONE);
                       holder.icon_conectado.setVisibility(View.GONE);

                       if (fecha.equals(dateFormat.format(c.getTime()))){
                            holder.tv_desconectado.setText("últ.vez hoy a las "+ hora);
                       }else {
                           holder.tv_desconectado.setText("últ.vez "+ fecha + "A las "+ hora);
                       }
                   }
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //referencio chart preferens
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

    public class viewholderAdaptersChatList extends RecyclerView.ViewHolder {

        TextView textousuario;
        ImageView img_Users;
        CardView cardView;

        TextView tv_conectado , tv_desconectado;
        ImageView icon_conectado , icon_desconectado;

        public viewholderAdaptersChatList(@NonNull View itemView) {
            super(itemView);
            textousuario = itemView.findViewById(R.id.textonameuser1);
            img_Users = itemView.findViewById(R.id.imguserchat1);
            cardView = itemView.findViewById(R.id.cardviewRow_Users);

            tv_conectado = itemView.findViewById(R.id.tvConectado);
            tv_desconectado = itemView.findViewById(R.id.tvDesconectado);
            icon_conectado = itemView.findViewById(R.id.ic_conectado);
            icon_desconectado = itemView.findViewById(R.id.icon_desconectado);

    }
}

}
