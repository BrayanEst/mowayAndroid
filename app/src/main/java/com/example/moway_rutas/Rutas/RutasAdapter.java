package com.example.moway_rutas.Rutas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moway_rutas.R;
import com.example.moway_rutas.interfaces.RecyclerViewClik;

import java.util.List;

public class RutasAdapter extends RecyclerView.Adapter<RutasAdapter.ViewHolder> {

    public List<RutasModelo> rutasModelosList;
    public Context context ;

    RecyclerViewClik recyclerViewClik;

    public RutasAdapter(List<RutasModelo> rutasModelos, Context context ,RecyclerViewClik  recyclerViewClik) {
        this.rutasModelosList = rutasModelos;
        this.context = context;
        this.recyclerViewClik = recyclerViewClik;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listado_rutas,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RutasAdapter.ViewHolder holder, int position) {
        final  RutasModelo item = rutasModelosList.get(position);

        holder.NumeroRuta.setText(rutasModelosList.get(position).getNumero_ruta());
        holder.nombre_recorrido.setText(rutasModelosList.get(position).getNombre_ruta());
        Glide.with(context)
                .load(rutasModelosList.get(position).getImagen_recorrido())
                .circleCrop()
                .into(holder.imagenRuta);

    }


    @Override
    public int getItemCount() {
        return rutasModelosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imagenRuta;
        private TextView NumeroRuta;
        private TextView nombre_recorrido;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            NumeroRuta =  itemView.findViewById(R.id.numero_empresa);
            imagenRuta =  itemView.findViewById(R.id.img_foto_ruta);
            nombre_recorrido =  itemView.findViewById(R.id.nombre_recorrido);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClik.reaccionItemClick(getAdapterPosition());
                }
            });



        }
    }
}
