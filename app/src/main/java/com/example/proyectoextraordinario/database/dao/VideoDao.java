package com.example.proyectoextraordinario.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proyectoextraordinario.models.Video;

import java.util.List;

/**
 * Interfaz DAO (Data Access Object) para realizar operaciones CRUD y consultas
 * específicas en la tabla `Video` de la base de datos.
 */
@Dao
public interface VideoDao {

    /**
     * Inserta un nuevo video en la base de datos.
     *
     * @param video Objeto de tipo `Video` que se desea insertar.
     */
    @Insert
    void insertarVideo(Video video);

    /**
     * Actualiza un video existente en la base de datos.
     *
     * @param video Objeto de tipo `Video` con los datos actualizados.
     */
    @Update
    void actualizarVideo(Video video);

    /**
     * Elimina un video de la base de datos.
     *
     * @param video Objeto de tipo `Video` que se desea eliminar.
     */
    @Delete
    void eliminarVideo(Video video);

    /**
     * Obtiene todos los videos almacenados en la base de datos.
     *
     * @return Lista de objetos `Video`.
     */
    @Query("SELECT * FROM Video")
    List<Video> obtenerTodosLosVideos();

    /**
     * Obtiene todos los videos marcados como favoritos.
     *
     * @return Lista de objetos `Video` que están marcados como favoritos.
     */
    @Query("SELECT * FROM Video WHERE favorito = 1")
    List<Video> obtenerVideosFavoritos();

    /**
     * Obtiene los videos que pertenecen a una categoría específica.
     *
     * @param categoria Categoría de los videos que se desean obtener.
     * @return Lista de objetos `Video` que pertenecen a la categoría especificada.
     */
    @Query("SELECT * FROM Video WHERE categoria = :categoria")
    List<Video> obtenerVideosPorCategoria(String categoria);

    /**
     * Verifica si un video específico está marcado como favorito.
     *
     * @param videoId ID del video que se desea verificar.
     * @return `true` si el video está marcado como favorito, de lo contrario `false`.
     */
    @Query("SELECT COUNT(*) > 0 FROM video WHERE id = :videoId AND favorito = 1")
    boolean esFavorito(int videoId);

    /**
     * Marca un video como favorito en la base de datos.
     *
     * @param videoId ID del video que se desea marcar como favorito.
     */
    @Query("UPDATE video SET favorito = 1 WHERE id = :videoId")
    void agregarAFavoritos(int videoId);

    /**
     * Elimina la marca de favorito de un video en la base de datos.
     *
     * @param videoId ID del video que se desea desmarcar como favorito.
     */
    @Query("UPDATE video SET favorito = 0 WHERE id = :videoId")
    void eliminarDeFavoritos(int videoId);
}