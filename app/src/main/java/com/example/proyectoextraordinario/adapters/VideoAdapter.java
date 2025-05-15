package com.example.proyectoextraordinario.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final List<Video> videos;
    private final LifecycleOwner lifecycleOwner;
    private final VideoDao videoDao;

    public VideoAdapter(List<Video> videos, LifecycleOwner lifecycleOwner, VideoDao videoDao) {
        this.videos = videos;
        this.lifecycleOwner = lifecycleOwner;
        this.videoDao = videoDao;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.tvTituloVideo.setText(video.getTitulo());
        holder.tvCategoriaVideo.setText(video.getCategoria());

        String videoId = extraerIdDeYoutube(video.getUrl());


        // Vincular el ciclo de vida del reproductor
        lifecycleOwner.getLifecycle().addObserver(holder.youtubePlayerView);

        holder.youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(videoId, 0);
            }
        });

        // Consultar si el video está en favoritos
        new Thread(() -> {
            boolean esFavorito = videoDao.esFavorito(video.getId()); // Método en el DAO
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
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    private String extraerIdDeYoutube(String url) {
        if (url != null && url.contains("v=")) {
            return url.substring(url.indexOf("v=") + 2, url.indexOf("&") > 0 ? url.indexOf("&") : url.length());
        }
        return url;
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTituloVideo, tvCategoriaVideo;
        YouTubePlayerView youtubePlayerView;
        ImageButton btnFavoritos;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTituloVideo = itemView.findViewById(R.id.tvTituloVideo);
            tvCategoriaVideo = itemView.findViewById(R.id.tvCategoriaVideo);
            youtubePlayerView = itemView.findViewById(R.id.youtubePlayerView);
            btnFavoritos = itemView.findViewById(R.id.btnFavoritos);
        }
    }
}