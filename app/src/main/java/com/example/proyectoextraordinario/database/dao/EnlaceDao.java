package com.example.proyectoextraordinario.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proyectoextraordinario.models.Enlace;

import java.util.List;

/**
 * Interfaz DAO (Data Access Object) para realizar operaciones CRUD y consultas
 * específicas en la tabla `Enlace` de la base de datos.
 */
@Dao
public interface EnlaceDao {

    /**
     * Inserta un nuevo enlace en la base de datos.
     *
     * @param enlace Objeto de tipo `Enlace` que se desea insertar.
     */
    @Insert
    void insertarEnlace(Enlace enlace);

    /**
     * Actualiza un enlace existente en la base de datos.
     *
     * @param enlace Objeto de tipo `Enlace` con los datos actualizados.
     */
    @Update
    void actualizarEnlace(Enlace enlace);

    /**
     * Elimina un enlace de la base de datos.
     *
     * @param enlace Objeto de tipo `Enlace` que se desea eliminar.
     */
    @Delete
    void eliminarEnlace(Enlace enlace);

    /**
     * Obtiene todos los enlaces almacenados en la base de datos.
     *
     * @return Lista de objetos `Enlace`.
     */
    @Query("SELECT * FROM Enlace")
    List<Enlace> obtenerTodosLosEnlaces();

    /**
     * Obtiene todos los enlaces marcados como favoritos.
     *
     * @return Lista de objetos `Enlace` que están marcados como favoritos.
     */
    @Query("SELECT * FROM Enlace WHERE favorito = 1")
    List<Enlace> obtenerEnlacesFavoritos();

    /**
     * Obtiene los enlaces que pertenecen a una categoría específica.
     *
     * @param categoria Categoría de los enlaces que se desean obtener.
     * @return Lista de objetos `Enlace` que pertenecen a la categoría especificada.
     */
    @Query("SELECT * FROM Enlace WHERE categoria = :categoria")
    List<Enlace> obtenerEnlacesPorCategoria(String categoria);

    /**
     * Verifica si un enlace específico está marcado como favorito.
     *
     * @param enlaceId ID del enlace que se desea verificar.
     * @return `true` si el enlace está marcado como favorito, de lo contrario `false`.
     */
    @Query("SELECT COUNT(*) > 0 FROM Enlace WHERE id = :enlaceId AND favorito = 1")
    boolean esFavorito(int enlaceId);

    /**
     * Marca un enlace como favorito en la base de datos.
     *
     * @param enlaceId ID del enlace que se desea marcar como favorito.
     */
    @Query("UPDATE Enlace SET favorito = 1 WHERE id = :enlaceId")
    void agregarAFavoritos(int enlaceId);

    /**
     * Elimina la marca de favorito de un enlace en la base de datos.
     *
     * @param enlaceId ID del enlace que se desea desmarcar como favorito.
     */
    @Query("UPDATE Enlace SET favorito = 0 WHERE id = :enlaceId")
    void eliminarDeFavoritos(int enlaceId);
}