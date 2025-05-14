package com.example.proyectoextraordinario.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoextraordinario.models.Enlace;
import com.example.proyectoextraordinario.R;

import java.util.List;

public class EnlaceAdapter extends RecyclerView.Adapter<EnlaceAdapter.EnlaceViewHolder> {

    private final List<Enlace> enlaces;

    public EnlaceAdapter(List<Enlace> enlaces) {
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
        holder.titulo.setText(enlace.titulo);
        holder.url.setText(enlace.url);
    }

    @Override
    public int getItemCount() {
        return enlaces.size();
    }

    static class EnlaceViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, url;

        public EnlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.tvTituloEnlace);
            url = itemView.findViewById(R.id.tvUrlEnlace);
        }
    }
}