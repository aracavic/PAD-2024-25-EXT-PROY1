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


/**
 * `SimpleVideoAdapter` es un adaptador para un `RecyclerView` que muestra una lista de videos simples.
 * Permite al usuario guardar videos en la base de datos mediante un botón.
 */
public class SimpleVideoAdapter extends RecyclerView.Adapter<SimpleVideoAdapter.SimpleVideoViewHolder> {

    // Lista de videos a mostrar
    private final List<Video> videos;

    // DAO para interactuar con la base de datos de videos
    private final VideoDao videoDao;

    /**
     * Constructor del adaptador.
     *
     * @param videos   Lista de videos a mostrar.
     * @param videoDao DAO para realizar operaciones en la base de datos.
     */
    public SimpleVideoAdapter(List<Video> videos, VideoDao videoDao) {
        this.videos = videos;
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
    public SimpleVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_video, parent, false);
        return new SimpleVideoViewHolder(view);
    }

    /**
     * Vincula los datos de un video a un ViewHolder.
     *
     * @param holder   ViewHolder que se va a actualizar.
     * @param position Posición del elemento en la lista.
     */
    @Override
    public void onBindViewHolder(@NonNull SimpleVideoViewHolder holder, int position) {
        Video video = videos.get(position);

        // Configurar los datos del video
        holder.tvTituloVideo.setText(video.getTitulo());
        holder.tvUrlVideoSimple.setText(video.getUrl());
        holder.tvCategoriaVideo.setText(video.getCategoria());

        // Configurar el botón de guardar
        holder.btnGuardar.setOnClickListener(v -> {
            new Thread(() -> {
                // Insertar el video en la base de datos
                videoDao.insertarVideo(video);

                // Mostrar un mensaje en el hilo principal
                holder.itemView.post(() ->
                        Toast.makeText(holder.itemView.getContext(), "Video guardado exitosamente", Toast.LENGTH_SHORT).show()
                );
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
     * ViewHolder para representar un elemento de la lista de videos.
     */
    public static class SimpleVideoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTituloVideo, tvCategoriaVideo, tvUrlVideoSimple;
        ImageButton btnGuardar;

        /**
         * Constructor del ViewHolder.
         *
         * @param itemView Vista del elemento.
         */
        public SimpleVideoViewHolder(@NonNull View itemView) {
            super(itemView);

            // Inicializar las vistas del elemento
            tvTituloVideo = itemView.findViewById(R.id.tvTituloVideoSimple);
            tvUrlVideoSimple = itemView.findViewById(R.id.tvUrlVideoSimple);
            tvCategoriaVideo = itemView.findViewById(R.id.tvCategoriaVideoSimple);
            btnGuardar = itemView.findViewById(R.id.btnGuardar);
        }
    }
}