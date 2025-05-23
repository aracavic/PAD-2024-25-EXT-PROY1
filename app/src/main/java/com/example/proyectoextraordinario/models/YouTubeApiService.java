package com.example.proyectoextraordinario.models;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YouTubeApiService {
    @GET("search")
    Call<YouTubeResponse> buscarVideos(
            @Query("part") String part,
            @Query("q") String query,
            @Query("type") String type,
            @Query("key") String apiKey,
            @Query("maxResults") int maxResults
    );
}
