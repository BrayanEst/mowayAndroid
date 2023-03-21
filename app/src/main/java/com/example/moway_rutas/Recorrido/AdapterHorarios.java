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

public class AdapterHorarios extends RecyclerView.Adapter<AdapterHorarios.viewHolderHorarios> {

    List<ModeloHorario>listaconductores;
    Context context;

    public AdapterHorarios(List<ModeloHorario> listaconductores, Context context) {
        this.listaconductores = listaconductores;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolderHorarios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_horarios,parent,false);

        return new viewHolderHorarios(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderHorarios holder, int position) {
        //dias de semana
        holder.iniciosemana.setText(listaconductores.get(position).getHora_inicio_semana());
        holder.finsemana.setText(listaconductores.get(position).getHora_final_semana());
        //fines de semana
        holder.inicioFinDeSemana.setText(listaconductores.get(position).getHora_inicio_festivo());
        holder.FinalFinDeSemana.setText(listaconductores.get(position).getHora_final_festivo());

    }

    @Override
    public int getItemCount() {
        return listaconductores.size();
    }

    public class viewHolderHorarios extends RecyclerView.ViewHolder {

        TextView iniciosemana ,finsemana ,inicioFinDeSemana , FinalFinDeSemana;
        public viewHolderHorarios(@NonNull View itemView) {
            super(itemView);

            iniciosemana = itemView.findViewById(R.id.inicioSemana);
            finsemana = itemView.findViewById(R.id.finSemana);

            inicioFinDeSemana = itemView.findViewById(R.id.iniciofinSemana);
            FinalFinDeSemana = itemView.findViewById(R.id.finalfinSemana);

        }
    }
}
