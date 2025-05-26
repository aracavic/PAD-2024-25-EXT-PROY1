package com.example.proyectoextraordinario.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoextraordinario.database.dao.EnlaceDao;
import com.example.proyectoextraordinario.models.Enlace;
import com.example.proyectoextraordinario.R;

import java.util.List;

/**
 * Adaptador para gestionar y mostrar una lista de enlaces en un RecyclerView.
 * Permite interactuar con los enlaces, como abrirlos, marcarlos como favoritos o eliminarlos.
 */
public class EnlaceAdapter extends RecyclerView.Adapter<EnlaceAdapter.EnlaceViewHolder> {

    // Lista de enlaces a mostrar
    private final List<Enlace> enlaces;

    // DAO para realizar operaciones en la base de datos
    private final EnlaceDao enlaceDao;

    /**
     * Constructor del adaptador.
     *
     * @param enlaces   Lista de enlaces a mostrar.
     * @param enlaceDao DAO para interactuar con la base de datos.
     */
    public EnlaceAdapter(List<Enlace> enlaces, EnlaceDao enlaceDao) {
        this.enlaceDao = enlaceDao;
        this.enlaces = enlaces;
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
    public EnlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_enlace, parent, false);
        return new EnlaceViewHolder(view);
    }

    /**
     * Vincula los datos de un enlace a un ViewHolder.
     *
     * @param holder   ViewHolder que se va a actualizar.
     * @param position Posición del elemento en la lista.
     */
    @Override
    public void onBindViewHolder(@NonNull EnlaceViewHolder holder, int position) {
        Enlace enlace = enlaces.get(position);

        // Configurar los datos del enlace
        holder.titulo.setText(enlace.getTitulo());
        holder.url.setText(enlace.getUrl());
        holder.categoria.setText(enlace.getCategoria());

        // Hacer que el enlace sea clickeable para abrirlo en un navegador
        holder.url.setOnClickListener(v -> {
            String url = enlace.getUrl();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            v.getContext().startActivity(intent);
        });

        // Verificar si el enlace es favorito y actualizar el botón
        new Thread(() -> {
            boolean esFavorito = enlaceDao.esFavorito(enlace.getId());
            holder.itemView.post(() -> {
                holder.btnFavoritos.setImageResource(esFavorito ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
                holder.btnFavoritos.setColorFilter(esFavorito ? Color.YELLOW : Color.BLACK);
            });
        }).start();

        // Alternar el estado de favorito al hacer clic en el botón
        holder.btnFavoritos.setOnClickListener(v -> {
            new Thread(() -> {
                boolean esFavorito = enlaceDao.esFavorito(enlace.getId());
                if (esFavorito) {
                    enlaceDao.eliminarDeFavoritos(enlace.getId());
                } else {
                    enlaceDao.agregarAFavoritos(enlace.getId());
                }
                holder.itemView.post(() -> {
                    holder.btnFavoritos.setImageResource(esFavorito ? R.drawable.ic_favorite_border : R.drawable.ic_favorite);
                    holder.btnFavoritos.setColorFilter(esFavorito ? Color.BLACK : Color.YELLOW);
                });
            }).start();
        });

        // Eliminar el enlace al hacer clic en el botón de borrar
        holder.btnBorrarEnlace.setOnClickListener(v -> {
            new Thread(() -> {
                enlaceDao.eliminarEnlace(enlace);
                enlaces.remove(position);
                holder.itemView.post(() -> notifyItemRemoved(position));
            }).start();
        });
    }

    /**
     * Devuelve el número de elementos en la lista.
     *
     * @return Cantidad de elementos.
     */
    @Override
    public int getItemCount() {
        return enlaces.size();
    }

    /**
     * ViewHolder para representar un elemento de la lista de enlaces.
     */
    static class EnlaceViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, url, categoria;
        ImageButton btnFavoritos;
        Button btnBorrarEnlace;

        /**
         * Constructor del ViewHolder.
         *
         * @param itemView Vista del elemento.
         */
        public EnlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.tvTituloEnlace);
            url = itemView.findViewById(R.id.tvUrlEnlace);
            btnFavoritos = itemView.findViewById(R.id.btnFavoritos);
            categoria = itemView.findViewById(R.id.tvCategoriaEnlace);
            btnBorrarEnlace = itemView.findViewById(R.id.btnBorrarEnlace);
        }
    }
}