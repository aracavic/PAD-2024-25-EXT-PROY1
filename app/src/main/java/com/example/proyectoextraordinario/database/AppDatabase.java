package com.example.proyectoextraordinario.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.proyectoextraordinario.database.dao.EnlaceDao;
import com.example.proyectoextraordinario.models.Enlace;
import com.example.proyectoextraordinario.models.Video;
import com.example.proyectoextraordinario.database.dao.VideoDao;

@Database(entities = {Enlace.class, Video.class}, version = 3) // Incrementa la versión
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE; // Declaración de la instancia

    public abstract EnlaceDao enlaceDao();
    public abstract VideoDao videoDao();

}