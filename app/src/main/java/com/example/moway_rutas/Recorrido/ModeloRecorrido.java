package com.example.moway_rutas.Recorrido;

public class ModeloRecorrido {

    private  String numero_ruta;
    private  String url_recorrido;
    private  String imagen_recorrido;
    private  String id;

    public ModeloRecorrido() {
    }

    public ModeloRecorrido(String numero_ruta, String url_recorrido, String imagen_recorrido, String id) {
        this.numero_ruta = numero_ruta;
        this.url_recorrido = url_recorrido;
        this.imagen_recorrido = imagen_recorrido;
        this.id = id;
    }

    public String getNumero_ruta() {
        return numero_ruta;
    }

    public String getUrl_recorrido() {
        return url_recorrido;
    }

    public String getImagen_recorrido() {
        return imagen_recorrido;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ModeloRecorrido{" +
                "\n numero_ruta='" + numero_ruta + '\'' +
                ",\n url_recorrido='" + url_recorrido + '\'' +
                ",\nimagen_recorrido='" + imagen_recorrido + '\'' +
                ",\n id='" + id + '\'' +
                '}';
    }
}
