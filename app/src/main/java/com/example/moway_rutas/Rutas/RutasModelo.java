package com.example.moway_rutas.Rutas;

import android.content.Context;

import java.io.Serializable;
import java.util.List;

public class RutasModelo implements Serializable {

    String numero_ruta;
    String url_recorrido;
    String imagen_recorrido;
    String nombre_ruta;
    String id;

    public RutasModelo() {
    }

    public RutasModelo(String numero_ruta, String url_recorrido, String imagen_recorrido, String nombre_ruta, String id) {
        this.numero_ruta = numero_ruta;
        this.url_recorrido = url_recorrido;
        this.imagen_recorrido = imagen_recorrido;
        this.nombre_ruta = nombre_ruta;
        this.id = id;
    }

    public String getNumero_ruta() {
        return numero_ruta;
    }

    public void setNumero_ruta(String numero_ruta) {
        this.numero_ruta = numero_ruta;
    }

    public String getUrl_recorrido() {
        return url_recorrido;
    }

    public void setUrl_recorrido(String url_recorrido) {
        this.url_recorrido = url_recorrido;
    }

    public String getImagen_recorrido() {
        return imagen_recorrido;
    }

    public void setImagen_recorrido(String imagen_recorrido) {
        this.imagen_recorrido = imagen_recorrido;
    }

    public String getNombre_ruta() {
        return nombre_ruta;
    }

    public void setNombre_ruta(String nombre_ruta) {
        this.nombre_ruta = nombre_ruta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RutasModelo{" +
                "numero_ruta='" + numero_ruta + '\'' +
                ", url_recorrido='" + url_recorrido + '\'' +
                ", imagen_recorrido='" + imagen_recorrido + '\'' +
                ", nombre_ruta='" + nombre_ruta + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
