package com.example.moway_rutas.Chat;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moway_rutas.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class holfderMensaje extends RecyclerView.ViewHolder {

    private TextView nombre;
    private TextView mensaje;
    private TextView hora;
    private CircleImageView fotoMensje_perfil;
    private ImageView fotomensaje;

    public holfderMensaje(@NonNull View itemView) {
        super(itemView);
        nombre = itemView.findViewById(R.id.nameusercardview);
        mensaje = itemView.findViewById(R.id.mensaje_Mensaje);
        hora = itemView.findViewById(R.id.Hora_Mensaje);
        fotoMensje_perfil = itemView.findViewById(R.id.cardViewFotoperfil);
        fotomensaje = itemView.findViewById(R.id.mensaje_Foto);
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getMensaje() {
        return mensaje;
    }

    public void setMensaje(TextView mensaje) {
        this.mensaje = mensaje;
    }

    public TextView getHora() {
        return hora;
    }

    public void setHora(TextView hora) {
        this.hora = hora;
    }

    public CircleImageView getFotoMensje_perfil() {
        return fotoMensje_perfil;
    }

    public void setFotoMensje_perfil(CircleImageView fotoMensje_perfil) {
        this.fotoMensje_perfil = fotoMensje_perfil;
    }

    public ImageView getFotomensaje() {
        return fotomensaje;
    }

    public void setFotomensaje(ImageView fotomensaje) {
        this.fotomensaje = fotomensaje;
    }
}
