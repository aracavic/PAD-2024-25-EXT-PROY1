package com.example.proyectoextraordinario.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.proyectoextraordinario.database.dao.EnlaceDao;
import com.example.proyectoextraordinario.models.Enlace;
import com.example.proyectoextraordinario.models.Video;
import com.example.proyectoextraordinario.database.dao.VideoDao;

/**
 * Clase que representa la base de datos de la aplicación utilizando Room.
 * Define las entidades y los DAOs asociados para interactuar con la base de datos.
 */
@Database(entities = {Enlace.class, Video.class}, version = 3) // Incrementa la versión
public abstract class AppDatabase extends RoomDatabase {

    /**
     * Instancia única de la base de datos para garantizar el patrón Singleton.
     */
    private static volatile AppDatabase INSTANCE;

    /**
     * Método abstracto para obtener el DAO de la entidad `Enlace`.
     *
     * @return Instancia de `EnlaceDao` para realizar operaciones en la tabla `Enlace`.
     */
    public abstract EnlaceDao enlaceDao();

    /**
     * Método abstracto para obtener el DAO de la entidad `Video`.
     *
     * @return Instancia de `VideoDao` para realizar operaciones en la tabla `Video`.
     */
    public abstract VideoDao videoDao();
}