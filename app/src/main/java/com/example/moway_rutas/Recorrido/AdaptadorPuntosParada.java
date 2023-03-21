package com.example.moway_rutas.Recorrido;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moway_rutas.R;

import java.util.List;

public class AdaptadorPuntosParada extends RecyclerView.Adapter<AdaptadorPuntosParada.viewholderParadas> {

    List<ModeloPuntosParada> listPutosParada;
    Context context;

    public AdaptadorPuntosParada(List<ModeloPuntosParada> listPutosParada, Context context) {
        this.listPutosParada = listPutosParada;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholderParadas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_puntos_parada,parent,false);
        return new viewholderParadas(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholderParadas holder, int position) {
        holder.nameconductores.setText(listPutosParada.get(position).getParada());
    }

    @Override
    public int getItemCount() {
        return listPutosParada.size();
    }

    public class viewholderParadas extends RecyclerView.ViewHolder {

        TextView nameconductores;
        public viewholderParadas(@NonNull View itemView) {
            super(itemView);

            nameconductores = itemView.findViewById(R.id.namePuntosparada);

        }
    }
}
