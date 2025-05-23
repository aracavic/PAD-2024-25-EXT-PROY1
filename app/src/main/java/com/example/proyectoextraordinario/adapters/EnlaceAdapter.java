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

public class EnlaceAdapter extends RecyclerView.Adapter<EnlaceAdapter.EnlaceViewHolder> {

    private final List<Enlace> enlaces;
    private final EnlaceDao enlaceDao;

    public EnlaceAdapter(List<Enlace> enlaces, EnlaceDao enlaceDao) {
        this.enlaceDao = enlaceDao;
        this.enlaces = enlaces;
    }

    @NonNull
    @Override
    public EnlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_enlace, parent, false);
        return new EnlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EnlaceViewHolder holder, int position) {
        Enlace enlace = enlaces.get(position);
        holder.titulo.setText(enlace.getTitulo());
        holder.url.setText(enlace.getUrl());
        holder.categoria.setText(enlace.getCategoria());

        // Hacer que el enlace sea clickeable
        holder.url.setOnClickListener(v -> {
            String url = enlace.getUrl();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            v.getContext().startActivity(intent);
        });

        new Thread(() -> {
            boolean esFavorito = enlaceDao.esFavorito(enlace.getId());
            holder.itemView.post(() -> {
                holder.btnFavoritos.setImageResource(esFavorito ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
                holder.btnFavoritos.setColorFilter(esFavorito ? Color.YELLOW : Color.BLACK);
            });
        }).start();

        // Configurar el evento de clic para alternar favoritos
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

        holder.btnBorrarEnlace.setOnClickListener(v -> {
            new Thread(() -> {
                enlaceDao.eliminarEnlace(enlace);
                enlaces.remove(position);
                holder.itemView.post(() -> notifyItemRemoved(position));
            }).start();
        });
    }

    @Override
    public int getItemCount() {
        return enlaces.size();
    }

    static class EnlaceViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, url, categoria;
        ImageButton btnFavoritos;
        Button btnBorrarEnlace;

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