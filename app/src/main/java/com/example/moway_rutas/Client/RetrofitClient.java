package com.example.moway_rutas.Client;

import com.example.moway_rutas.interfaces.Producto_API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String URL_BASE= "http://appmoway.herokuapp.com/api/";
    private static Retrofit retrofit;

    public static Producto_API getApiService(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(Producto_API.class);
    }
}
