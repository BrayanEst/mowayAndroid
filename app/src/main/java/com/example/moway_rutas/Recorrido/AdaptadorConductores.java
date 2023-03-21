package com.example.moway_rutas.Recorrido;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.TaskExecutor;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moway_rutas.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorConductores extends RecyclerView.Adapter<AdaptadorConductores.viewholderConductores> {

    List<ModeloConductores> listConductores ;
    Context context;

    public AdaptadorConductores(List<ModeloConductores> listConductores, Context context) {
        this.listConductores = listConductores;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholderConductores onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_conductores,parent,false);

        return new viewholderConductores(view);
    }
    @Override
    public void onBindViewHolder(@NonNull viewholderConductores holder, int position) {
        holder.txt_name_conductor.setText(listConductores.get(position).getNombre());

        Glide.with(context)
                .load(listConductores.get(position)
                        .getFoto())
                        .into(holder.circleimg_conductor);

    }

    @Override
    public int getItemCount() {
        return listConductores.size();
    }

    public class viewholderConductores extends RecyclerView.ViewHolder {

        CircleImageView circleimg_conductor;
        TextView txt_name_conductor;

        public viewholderConductores(@NonNull View itemView) {
            super(itemView);

            circleimg_conductor = itemView.findViewById(R.id.imagenconductores);
            txt_name_conductor = itemView.findViewById(R.id.Name_conductores);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
