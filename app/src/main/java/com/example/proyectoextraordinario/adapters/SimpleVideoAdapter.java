package com.example.proyectoextraordinario.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoextraordinario.R;
import com.example.proyectoextraordinario.database.dao.VideoDao;
import com.example.proyectoextraordinario.models.Video;

import java.util.List;
import java.util.concurrent.Executors;

public class SimpleVideoAdapter extends RecyclerView.Adapter<SimpleVideoAdapter.SimpleVideoViewHolder> {

    private final List<Video> videos;
    private final VideoDao videoDao;

    public SimpleVideoAdapter(List<Video> videos, VideoDao videoDao) {
        this.videos = videos;
        this.videoDao = videoDao;
    }

    @NonNull
    @Override
    public SimpleVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_video, parent, false);
        return new SimpleVideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleVideoViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.tvTituloVideo.setText(video.getTitulo());
        holder.tvUrlVideoSimple.setText(video.getUrl());
        holder.tvCategoriaVideo.setText(video.getCategoria());

        // Configurar el botón de guardar
        holder.btnGuardar.setOnClickListener(v -> {
            new Thread(() -> {
                videoDao.insertarVideo(video);
                // Mostrar mensaje en el hilo principal
                holder.itemView.post(() ->
                        Toast.makeText(holder.itemView.getContext(), "Video guardado exitosamente", Toast.LENGTH_SHORT).show()
                );
            }).start();
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public static class SimpleVideoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTituloVideo, tvCategoriaVideo, tvUrlVideoSimple;
        ImageButton btnGuardar;

        public SimpleVideoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTituloVideo = itemView.findViewById(R.id.tvTituloVideoSimple);
            tvUrlVideoSimple = itemView.findViewById(R.id.tvUrlVideoSimple);
            tvCategoriaVideo = itemView.findViewById(R.id.tvCategoriaVideoSimple);
            btnGuardar = itemView.findViewById(R.id.btnGuardar);
        }
    }
}