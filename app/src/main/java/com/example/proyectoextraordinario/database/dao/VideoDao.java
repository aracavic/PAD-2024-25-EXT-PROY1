package com.example.proyectoextraordinario.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proyectoextraordinario.models.Video;

import java.util.List;

@Dao
public interface VideoDao {
    @Insert
    void insertarVideo(Video video);

    @Update
    void actualizarVideo(Video video);

    @Delete
    void eliminarVideo(Video video);

    @Query("SELECT * FROM Video")
    List<Video> obtenerTodosLosVideos();

    @Query("SELECT * FROM Video WHERE favorito = 1")
    List<Video> obtenerVideosFavoritos();

    @Query("SELECT * FROM Video WHERE categoria = :categoria")
    List<Video> obtenerVideosPorCategoria(String categoria);
}
