package com.example.proyectoextraordinario.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoextraordinario.R;
import com.example.proyectoextraordinario.database.dao.VideoDao;
import com.example.proyectoextraordinario.models.Video;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

/**
 * Adaptador para un RecyclerView que muestra una lista de videos.
 * Permite reproducir videos de YouTube, marcarlos como favoritos y eliminarlos.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final List<Video> videos; // Lista de videos a mostrar
    private final LifecycleOwner lifecycleOwner; // Ciclo de vida del propietario (actividad o fragmento)
    private final VideoDao videoDao; // DAO para interactuar con la base de datos

    /**
     * Constructor del adaptador.
     *
     * @param videos         Lista de videos a mostrar.
     * @param lifecycleOwner Propietario del ciclo de vida para gestionar el reproductor de YouTube.
     * @param videoDao       DAO para realizar operaciones en la base de datos.
     */
    public VideoAdapter(List<Video> videos, LifecycleOwner lifecycleOwner, VideoDao videoDao) {
        this.videos = videos;
        this.lifecycleOwner = lifecycleOwner;
        this.videoDao = videoDao;
    }

    /**
     * Crea una nueva vista para un elemento del RecyclerView.
     *
     * @param parent   Vista padre.
     * @param viewType Tipo de vista.
     * @return ViewHolder para el elemento.
     */
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    /**
     * Vincula los datos de un video a un ViewHolder.
     *
     * @param holder   ViewHolder que se va a actualizar.
     * @param position Posición del elemento en la lista.
     */
    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.tvTituloVideo.setText(video.getTitulo());
        holder.tvCategoriaVideo.setText(video.getCategoria());

        String videoId = extraerIdDeYoutube(video.getUrl());

        // Vincular el ciclo de vida del reproductor
        lifecycleOwner.getLifecycle().addObserver(holder.youtubePlayerView);

        // Configurar el reproductor de YouTube
        holder.youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(videoId, 0);
            }
        });

        // Consultar si el video está en favoritos
        new Thread(() -> {
            boolean esFavorito = videoDao.esFavorito(video.getId());
            holder.itemView.post(() -> {
                holder.btnFavoritos.setImageResource(esFavorito ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
                holder.btnFavoritos.setColorFilter(esFavorito ? Color.YELLOW : Color.BLACK);
            });
        }).start();

        // Configurar el evento de clic para alternar favoritos
        holder.btnFavoritos.setOnClickListener(v -> {
            new Thread(() -> {
                boolean esFavorito = videoDao.esFavorito(video.getId());
                if (esFavorito) {
                    videoDao.eliminarDeFavoritos(video.getId());
                } else {
                    videoDao.agregarAFavoritos(video.getId());
                }
                holder.itemView.post(() -> {
                    holder.btnFavoritos.setImageResource(esFavorito ? R.drawable.ic_favorite_border : R.drawable.ic_favorite);
                    holder.btnFavoritos.setColorFilter(esFavorito ? Color.BLACK : Color.YELLOW);
                });
            }).start();
        });

        // Configurar el botón para eliminar el video
        holder.btnBorrarVideo.setOnClickListener(v -> {
            new Thread(() -> {
                videoDao.eliminarVideo(video);
                videos.remove(position);
                holder.itemView.post(() -> notifyItemRemoved(position));
            }).start();
        });
    }

    /**
     * Devuelve el número de elementos en la lista.
     *
     * @return Cantidad de videos.
     */
    @Override
    public int getItemCount() {
        return videos.size();
    }

    /**
     * Extrae el ID de un video de YouTube a partir de su URL.
     *
     * @param url URL del video de YouTube.
     * @return ID del video.
     */
    private String extraerIdDeYoutube(String url) {
        if (url != null && url.contains("v=")) {
            return url.substring(url.indexOf("v=") + 2, url.indexOf("&") > 0 ? url.indexOf("&") : url.length());
        }
        return url;
    }

    /**
     * ViewHolder para representar un elemento de la lista de videos.
     */
    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTituloVideo, tvCategoriaVideo; // Título y categoría del video
        YouTubePlayerView youtubePlayerView; // Reproductor de YouTube
        ImageButton btnFavoritos; // Botón para marcar como favorito
        Button btnBorrarVideo; // Botón para eliminar el video

        /**
         * Constructor del ViewHolder.
         *
         * @param itemView Vista del elemento.
         */
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTituloVideo = itemView.findViewById(R.id.tvTituloVideo);
            tvCategoriaVideo = itemView.findViewById(R.id.tvCategoriaVideo);
            youtubePlayerView = itemView.findViewById(R.id.youtubePlayerView);
            btnFavoritos = itemView.findViewById(R.id.btnFavoritos);
            btnBorrarVideo = itemView.findViewById(R.id.btnBorrarVideo);
        }
    }
}