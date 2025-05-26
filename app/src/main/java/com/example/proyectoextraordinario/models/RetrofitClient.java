package com.example.proyectoextraordinario.models;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase que proporciona una instancia de Retrofit configurada para interactuar con la API de YouTube.
 * Implementa el patrón Singleton para garantizar que solo exista una instancia de Retrofit.
 */
public class RetrofitClient {

    /**
     * URL base de la API de YouTube.
     */
    private static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";

    /**
     * Instancia única de Retrofit.
     */
    private static Retrofit retrofit;

    /**
     * Obtiene la instancia única de Retrofit. Si no existe, la crea y la configura.
     *
     * @return Instancia de Retrofit configurada con la URL base y el convertidor Gson.
     */
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}