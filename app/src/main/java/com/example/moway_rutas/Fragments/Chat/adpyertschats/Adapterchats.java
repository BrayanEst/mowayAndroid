package com.example.moway_rutas.Fragments.Chat.adpyertschats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moway_rutas.Fragments.Chat.modelos.Chats;
import com.example.moway_rutas.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Adapterchats extends RecyclerView.Adapter<Adapterchats.ViewHolderAdapter> {


   List<Chats>chatsList;
    Context context;

    //para aclar de donde viene el mensje
    public static final int MENSAJE_RIGHT = 1;
    public static final int MENSAJE_LEFT = 0;

    Boolean soloright = false;

    FirebaseUser fuser;
    public Adapterchats(List<Chats> chatsList, Context context) {
        this.chatsList = chatsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == MENSAJE_RIGHT){
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_rin,parent,false);
            return new Adapterchats.ViewHolderAdapter(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left,parent,false);
            return new Adapterchats.ViewHolderAdapter(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdapter holder, int position) {

        Chats chats = chatsList.get(position);

        holder.tv_mensaje.setText(chats.getMensaje());

        if (soloright){

            if (chats.getVisto().equals("si")){
                holder.img_entregado.setVisibility(View.GONE);
                holder.img_visto.setVisibility(View.VISIBLE);
            }else{
                holder.img_entregado.setVisibility(View.VISIBLE);
                holder.img_visto.setVisibility(View.GONE);
            }
            final Calendar c = Calendar.getInstance();
            final SimpleDateFormat  dateformat = new SimpleDateFormat("DD/MM/yyyy");

            if (chats.getFecha().equals(dateformat.format(c.getTime()))){
                holder.tv_fecha.setText(""+chats.getHora());
            }else{
                holder.tv_fecha.setText(""+chats.getFecha()+" "+chats.getHora());
            }

        }//fin del soloright

    }

    @Override
    public int getItemCount() {
        return chatsList.size();
    }

    public class ViewHolderAdapter extends RecyclerView.ViewHolder {

        TextView tv_mensaje , tv_fecha ;
        ImageView img_entregado , img_visto;
        public ViewHolderAdapter(@NonNull View itemView) {
            super(itemView);
            tv_mensaje = itemView.findViewById(R.id.tvmensaje);
            tv_fecha = itemView.findViewById(R.id.tv_fecha);
            img_entregado = itemView.findViewById(R.id.img_entregado);
            img_visto = itemView.findViewById(R.id.img_visto);
        }

    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (chatsList.get(position).getEnvia().equals(fuser.getUid())){
            soloright = true;
            return MENSAJE_RIGHT;
        }else {
            soloright = false;
            return  MENSAJE_LEFT;
        }
    }
}
