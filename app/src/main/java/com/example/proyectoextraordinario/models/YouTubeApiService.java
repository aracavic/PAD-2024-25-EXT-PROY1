package com.example.proyectoextraordinario.models;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interfaz que define los métodos para interactuar con la API de YouTube.
 * Utiliza Retrofit para realizar solicitudes HTTP.
 */
public interface YouTubeApiService {

    /**
     * Realiza una búsqueda de videos en la API de YouTube.
     *
     * @param part       Parte de la respuesta que se desea recuperar (por ejemplo, "snippet").
     * @param query      Palabra clave o término de búsqueda.
     * @param type       Tipo de recurso a buscar (por ejemplo, "video").
     * @param apiKey     Clave de la API para autenticar la solicitud.
     * @param maxResults Número máximo de resultados a devolver.
     * @return Llamada de Retrofit que devuelve una respuesta de tipo `YouTubeResponse`.
     */
    @GET("search")
    Call<YouTubeResponse> buscarVideos(
            @Query("part") String part,
            @Query("q") String query,
            @Query("type") String type,
            @Query("key") String apiKey,
            @Query("maxResults") int maxResults
    );
}