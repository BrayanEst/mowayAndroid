package com.example.moway_rutas.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moway_rutas.Fragments.Chat.modelos.Chats;
import com.example.moway_rutas.Fragments.Chat.modelos.UsersChat;
import com.example.moway_rutas.R;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterMensajes extends RecyclerView.Adapter<holfderMensaje> {

    private List<Mensaje>listmensaje = new ArrayList<>();
    private List<UsersChat>listauserchat = new ArrayList<>();
    private Context c ;

    //para aclar de donde viene el mensje
    public static final int MENSAJE_userI = 1;
    public static final int MENSAJE_userYou= 0;

    Boolean soloright = false;



    public AdapterMensajes(Context c) {
        this.c = c;
    }

    public void addMensaje(Mensaje m){
        listmensaje.add(m);
        notifyItemInserted(listmensaje.size());
    }

    @NonNull
    @Override
    public holfderMensaje onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_view_mensajes,parent,false);
        return new holfderMensaje(v);
    }

    @Override
    public void onBindViewHolder(@NonNull holfderMensaje holder, int position) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //UsersChat userss = listauserchat.get(position);

        holder.getMensaje().setText(listmensaje.get(position).getMensaje());
        holder.getHora().setText(listmensaje.get(position).getHora().toString());


            holder.getNombre().setText(user.getDisplayName());

            Glide.with(c)
                    .load(user.getPhotoUrl())
                    .into(holder.getFotoMensje_perfil());

    }

    @Override
    public int getItemCount() {
        return listmensaje.size();
    }
}
