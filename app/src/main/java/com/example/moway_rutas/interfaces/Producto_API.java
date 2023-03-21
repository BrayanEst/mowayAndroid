package com.example.moway_rutas.interfaces;

import com.example.moway_rutas.Pojos.Empresas_Pojo;
import com.example.moway_rutas.Recorrido.ModeloConductores;
import com.example.moway_rutas.Recorrido.ModeloHorario;
import com.example.moway_rutas.Recorrido.ModeloPuntosParada;
import com.example.moway_rutas.Recorrido.ModeloRecorrido;
import com.example.moway_rutas.Rutas.RutasModelo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Producto_API {

    //llamo a call le paso mi pojo y le coloco un nombre a mi metodo
    @GET("empresa_consulta_name")
    Call<List<Empresas_Pojo>> getEmpresa();

    //consulta Rutas
    @GET("recorrido_consulta/{id}")
    Call<List<RutasModelo>> getRecorrido_consulta(@Path("id") String id);

    //consulta recorrido
    @GET("recorrido_consulta_imagen_ruta_empresa/{id}/{id_recorrido}")
    Call<List<ModeloRecorrido>>getConsultaRecorrido(@Path("id")String id, @Path("id_recorrido")String id_recorrido);

    //consulta conductores
    @GET("conductor_recorrido/{id}")
    Call<List<ModeloConductores>>getConsulta_Conductores(@Path("id") String id);

    //consulta paradas
    @GET("recorrido_parada/{id}")
    Call<List<ModeloPuntosParada>>get_putos_parada(@Path("id") String id);

    //consulta horarios
    @GET("recorrido_horario/{id}")
    public Call<List<ModeloHorario>>gethorarios(@Path("id") String id);

}
