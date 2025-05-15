package com.example.proyectoextraordinario.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Video {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String titulo;
    private String url;
    private String categoria;
    private boolean favorito;

    // Constructor
    public Video(String titulo, String url, String categoria, boolean favorito) {
        this.titulo = titulo;
        this.url = url;
        this.categoria = categoria;
        this.favorito = favorito;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }
}