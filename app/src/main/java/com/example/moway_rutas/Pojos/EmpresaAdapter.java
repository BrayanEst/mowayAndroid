package com.example.moway_rutas.Pojos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moway_rutas.Fragments.FragmentReturRutas;
import com.example.moway_rutas.R;
import com.example.moway_rutas.Rutas.RutasModelo;
import com.example.moway_rutas.interfaces.RecyclerViewClik;


import java.util.List;

public class EmpresaAdapter extends RecyclerView.Adapter<EmpresaAdapter.viewHolder>{

    public List<Empresas_Pojo> empresasPojoList;
    public Context context ;

    public RecyclerViewClik recyclerViewClik;
    public static String datas;

    public EmpresaAdapter(List<Empresas_Pojo> empresasPojoList, Context context, RecyclerViewClik recyclerViewClik) {
        this.empresasPojoList = empresasPojoList;
        this.context = context;
        this.recyclerViewClik = recyclerViewClik;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listado_empresas,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final  Empresas_Pojo item = empresasPojoList.get(position);

        holder.name_empresa.setText(empresasPojoList.get(position).getNombre_empresa());
        Glide.with(context)
                .load(empresasPojoList.get(position).getImg_empresa())
                .centerCrop()
                .into(holder.img_foto_empresa);
    }

    @Override
    public int getItemCount() {

        return empresasPojoList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {

        private ImageView img_foto_empresa;
        private TextView name_empresa;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            img_foto_empresa = itemView.findViewById(R.id.img_foto_empresa);
            name_empresa = itemView.findViewById(R.id.name_empresa);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClik.reaccionItemClick(getAdapterPosition());
                }
            });
        }
    }
}
