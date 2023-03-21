package com.example.moway_rutas.Pojos;

import java.io.Serializable;

public class Empresas_Pojo implements Serializable {
    public String id ;
    public String nombre_empresa;
    public String img_empresa;

    public Empresas_Pojo() { }

    public Empresas_Pojo(String id, String nombre_empresa, String img_empresa) {
        this.id = id;
        this.nombre_empresa = nombre_empresa;
        this.img_empresa = img_empresa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getImg_empresa() {
        return img_empresa;
    }

    public void setImg_empresa(String img_empresa) {
        this.img_empresa = img_empresa;
    }

    @Override
    public String toString() {
        return "Empresas_Pojo{" +
                "id='" + id + '\'' +
                ", nombre_empresa='" + nombre_empresa + '\'' +
                ", img_empresa='" + img_empresa + '\'' +
                '}';
    }
}
