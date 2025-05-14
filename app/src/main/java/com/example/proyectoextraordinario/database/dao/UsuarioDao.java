package com.example.proyectoextraordinario.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.proyectoextraordinario.models.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {
    @Insert
    void insertarUsuario(Usuario usuario);

    @Query("SELECT * FROM Usuario")
    List<Usuario> obtenerTodosLosUsuarios();

    @Query("SELECT * FROM Usuario WHERE nombreUsuario = :nombreUsuario LIMIT 1")
    Usuario buscarUsuarioPorNombre(String nombreUsuario);
}
