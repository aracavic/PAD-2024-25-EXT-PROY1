package com.example.proyectoextraordinario.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proyectoextraordinario.models.Enlace;

import java.util.List;

@Dao
public interface EnlaceDao {
    @Insert
    void insertarEnlace(Enlace enlace);

    @Update
    void actualizarEnlace(Enlace enlace);

    @Delete
    void eliminarEnlace(Enlace enlace);

    @Query("SELECT * FROM Enlace")
    List<Enlace> obtenerTodosLosEnlaces();

    @Query("SELECT * FROM Enlace WHERE favorito = 1")
    List<Enlace> obtenerEnlacesFavoritos();

    @Query("SELECT * FROM Enlace WHERE categoria = :categoria")
    List<Enlace> obtenerEnlacesPorCategoria(String categoria);
}
