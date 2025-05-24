package com.example.proyectoextraordinario.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class YouTubeResponse {
    @SerializedName("items")
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public static class Item {
        @SerializedName("id")
        private Id id;

        @SerializedName("snippet")
        private Snippet snippet;

        public Id getId() {
            return id;
        }

        public Snippet getSnippet() {
            return snippet;
        }

        public static class Id {
            @SerializedName("videoId")
            private String videoId;

            public String getVideoId() {
                return videoId;
            }
        }

        public static class Snippet {
            @SerializedName("title")
            private String title;


            public String getTitle() {
                return title;
            }

        }
    }
}