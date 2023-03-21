package com.example.moway_rutas.interfaces;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyapiAdapter {

    private static Producto_API API;

    public static MyapiAdapter getApiService(){

        //Creamosun interceptory le indiicamosel log level a usar

        HttpLoggingInterceptor Loagin = new HttpLoggingInterceptor();
        Loagin.setLevel(HttpLoggingInterceptor.Level.BODY);

        //Asociamos el interceptor a las peticiones
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(Loagin);

        String baseUrl ="https://appmoway.herokuapp.com/api/";

        if (API == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            API = retrofit.create(Producto_API.class);
        }

        return (MyapiAdapter) API;

    }

}
