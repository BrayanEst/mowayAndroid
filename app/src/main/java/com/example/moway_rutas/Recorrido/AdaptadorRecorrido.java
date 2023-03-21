package com.example.moway_rutas.Recorrido;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moway_rutas.Rutas.RutasModelo;
import com.example.moway_rutas.interfaces.RecyclerViewClik;

import java.util.List;

public class AdaptadorRecorrido extends RecyclerView.Adapter<AdaptadorRecorrido.viewHolderRecorridos> {

    public List<RutasModelo> listaRecorridos;
    public Context context ;

    RecyclerViewClik recyclerViewClik;

    public AdaptadorRecorrido(List<RutasModelo> listaRecorridos, Context context, RecyclerViewClik recyclerViewClik) {
        this.listaRecorridos = listaRecorridos;
        this.context = context;
        this.recyclerViewClik = recyclerViewClik;
    }

    @NonNull
    @Override
    public viewHolderRecorridos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderRecorridos holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class viewHolderRecorridos extends RecyclerView.ViewHolder {
        public viewHolderRecorridos(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClik.reaccionItemClick(getAdapterPosition());
                }
            });
        }
    }
}
