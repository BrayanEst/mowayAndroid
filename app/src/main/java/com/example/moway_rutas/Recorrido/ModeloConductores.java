package com.example.moway_rutas.Recorrido;

public class ModeloConductores {

    private String nombre;
    private String apellido;
    private String foto;

    public ModeloConductores() {
    }

    public ModeloConductores(String nombre, String apellido, String foto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "ModeloConductores{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}
