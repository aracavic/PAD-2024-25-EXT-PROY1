package com.example.proyectoextraordinario.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "nombreUsuario")
    public String nombreUsuario;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "contrasena")
    public String contrasena;
}
