package com.example.proyectoextraordinario.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Clase que representa la respuesta de la API de YouTube.
 * Contiene una lista de elementos que incluyen información sobre los videos.
 */
public class YouTubeResponse {

    /**
     * Lista de elementos devueltos por la API de YouTube.
     */
    @SerializedName("items")
    private List<Item> items;

    /**
     * Obtiene la lista de elementos de la respuesta.
     *
     * @return Lista de objetos `Item`.
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Clase que representa un elemento individual en la respuesta de la API.
     */
    public static class Item {

        /**
         * Identificador del video.
         */
        @SerializedName("id")
        private Id id;

        /**
         * Información del video, como el título.
         */
        @SerializedName("snippet")
        private Snippet snippet;

        /**
         * Obtiene el identificador del video.
         *
         * @return Objeto `Id` que contiene el identificador del video.
         */
        public Id getId() {
            return id;
        }

        /**
         * Obtiene el snippet del video.
         *
         * @return Objeto `Snippet` que contiene información del video.
         */
        public Snippet getSnippet() {
            return snippet;
        }

        /**
         * Clase que representa el identificador del video.
         */
        public static class Id {

            /**
             * Identificador único del video en YouTube.
             */
            @SerializedName("videoId")
            private String videoId;

            /**
             * Obtiene el identificador único del video.
             *
             * @return Identificador del video.
             */
            public String getVideoId() {
                return videoId;
            }
        }

        /**
         * Clase que representa el snippet del video.
         * Contiene información como el título del video.
         */
        public static class Snippet {

            /**
             * Título del video.
             */
            @SerializedName("title")
            private String title;

            /**
             * Obtiene el título del video.
             *
             * @return Título del video.
             */
            public String getTitle() {
                return title;
            }
        }
    }
}