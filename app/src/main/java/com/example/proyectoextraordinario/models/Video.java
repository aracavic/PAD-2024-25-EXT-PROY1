package com.example.proyectoextraordinario.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Clase que representa un video en la base de datos.
 * Utiliza Room para mapear esta clase como una entidad en la base de datos.
 */
@Entity
public class Video {

    /**
     * Identificador único del video. Se genera automáticamente.
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * Título del video.
     */
    private String titulo;

    /**
     * URL del video.
     */
    private String url;

    /**
     * Categoría a la que pertenece el video.
     */
    private String categoria;

    /**
     * Indica si el video está marcado como favorito.
     */
    private boolean favorito;

    /**
     * Constructor de la clase Video.
     *
     * @param titulo    Título del video.
     * @param url       URL del video.
     * @param categoria Categoría del video.
     * @param favorito  Indica si el video es favorito.
     */
    public Video(String titulo, String url, String categoria, boolean favorito) {
        this.titulo = titulo;
        this.url = url;
        this.categoria = categoria;
        this.favorito = favorito;
    }

    /**
     * Obtiene el identificador del video.
     *
     * @return ID del video.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del video.
     *
     * @param id ID del video.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el título del video.
     *
     * @return Título del video.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título del video.
     *
     * @param titulo Título del video.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene la URL del video.
     *
     * @return URL del video.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece la URL del video.
     *
     * @param url URL del video.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Obtiene la categoría del video.
     *
     * @return Categoría del video.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Establece la categoría del video.
     *
     * @param categoria Categoría del video.
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Verifica si el video está marcado como favorito.
     *
     * @return `true` si el video es favorito, de lo contrario `false`.
     */
    public boolean isFavorito() {
        return favorito;
    }

    /**
     * Establece si el video es favorito.
     *
     * @param favorito `true` para marcar como favorito, `false` de lo contrario.
     */
    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }
}