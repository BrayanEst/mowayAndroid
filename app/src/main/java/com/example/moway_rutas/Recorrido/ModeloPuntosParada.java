package com.example.moway_rutas.Recorrido;

public class ModeloPuntosParada {
    private String parada;

    public ModeloPuntosParada() {
    }

    public ModeloPuntosParada(String parada) {
        this.parada = parada;
    }

    public String getParada() {
        return parada;
    }

    public void setParada(String parada) {
        this.parada = parada;
    }

    @Override
    public String toString() {
        return "ModeloPuntosParada{" +
                "parada='" + parada + '\'' +
                '}';
    }
}
