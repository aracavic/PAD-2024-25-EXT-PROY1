package com.example.proyectoextraordinario.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Clase que representa un enlace en la base de datos.
 * Utiliza Room para mapear esta clase como una entidad en la base de datos.
 */
@Entity
public class Enlace {

    /**
     * Identificador único del enlace. Se genera automáticamente.
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * Título del enlace.
     */
    private String titulo;

    /**
     * URL del enlace.
     */
    private String url;

    /**
     * Categoría a la que pertenece el enlace.
     */
    private String categoria;

    /**
     * Indica si el enlace está marcado como favorito.
     */
    private boolean favorito;

    /**
     * Constructor de la clase Enlace.
     *
     * @param titulo    Título del enlace.
     * @param url       URL del enlace.
     * @param categoria Categoría del enlace.
     * @param favorito  Indica si el enlace es favorito.
     */
    public Enlace(String titulo, String url, String categoria, boolean favorito) {
        this.titulo = titulo;
        this.url = url;
        this.categoria = categoria;
        this.favorito = favorito;
    }

    /**
     * Obtiene el identificador del enlace.
     *
     * @return ID del enlace.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del enlace.
     *
     * @param id ID del enlace.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el título del enlace.
     *
     * @return Título del enlace.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título del enlace.
     *
     * @param titulo Título del enlace.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene la URL del enlace.
     *
     * @return URL del enlace.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece la URL del enlace.
     *
     * @param url URL del enlace.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Obtiene la categoría del enlace.
     *
     * @return Categoría del enlace.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Establece la categoría del enlace.
     *
     * @param categoria Categoría del enlace.
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Verifica si el enlace está marcado como favorito.
     *
     * @return `true` si el enlace es favorito, de lo contrario `false`.
     */
    public boolean isFavorito() {
        return favorito;
    }

    /**
     * Establece si el enlace es favorito.
     *
     * @param favorito `true` para marcar como favorito, `false` de lo contrario.
     */
    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }
}