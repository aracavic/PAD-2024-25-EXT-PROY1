package com.example.proyectoextraordinario.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Video {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String titulo;
    public String url;
    public String categoria;
    public boolean favorito;

    // Constructor
    public Video(String titulo, String url, String categoria, boolean favorito) {
        this.titulo = titulo;
        this.url = url;
        this.categoria = categoria;
        this.favorito = favorito;
    }
}
