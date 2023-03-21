package com.example.moway_rutas.Fragments.Chat.modelos;

public class UsersChat {
    private String  id ;
    private String  nombre ;
    private String  mail ;
    private String  foto ;
    private String  estado ;
    private String  fecha ;
    private String  hora ;
    private int  soliicitudes ;
    private int  nuevomensaje ;

    public UsersChat() {
    }

    public UsersChat(String id, String nombre, String mail, String foto, String estado, String fecha, String hora, int soliicitudes, int nuevomensaje) {
        this.id = id;
        this.nombre = nombre;
        this.mail = mail;
        this.foto = foto;
        this.estado = estado;
        this.fecha = fecha;
        this.hora = hora;
        this.soliicitudes = soliicitudes;
        this.nuevomensaje = nuevomensaje;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getSoliicitudes() {
        return soliicitudes;
    }

    public void setSoliicitudes(int soliicitudes) {
        this.soliicitudes = soliicitudes;
    }

    public int getNuevomensaje() {
        return nuevomensaje;
    }

    public void setNuevomensaje(int nuevomensaje) {
        this.nuevomensaje = nuevomensaje;
    }
}
