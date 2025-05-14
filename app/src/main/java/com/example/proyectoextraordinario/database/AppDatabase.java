package com.example.proyectoextraordinario.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.proyectoextraordinario.models.Enlace;
import com.example.proyectoextraordinario.database.dao.EnlaceDao;
import com.example.proyectoextraordinario.models.Usuario;
import com.example.proyectoextraordinario.database.dao.UsuarioDao;
import com.example.proyectoextraordinario.models.Video;
import com.example.proyectoextraordinario.database.dao.VideoDao;

@Database(entities = {Usuario.class, Enlace.class, Video.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsuarioDao usuarioDao();
    public abstract EnlaceDao enlaceDao();
    public abstract VideoDao videoDao();
}