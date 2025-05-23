package com.example.proyectoextraordinario.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.proyectoextraordinario.database.AppDatabase;
import com.example.proyectoextraordinario.R;
import com.example.proyectoextraordinario.models.Video;
import com.example.proyectoextraordinario.adapters.VideoAdapter;

import java.util.List;

public class ListaVideosActivity extends AppCompatActivity {

    private RecyclerView rvListaVideos;
    private VideoAdapter videoAdapter;
    private AppDatabase db;

    @Override
    protected void onResume() {
        super.onResume();

        // Recargar los datos de la base de datos
        new Thread(() -> {
            List<Video> videos = db.videoDao().obtenerTodosLosVideos();
            runOnUiThread(() -> {
                // Actualizar los datos del adaptador
                videoAdapter = new VideoAdapter(videos, this, db.videoDao());
                rvListaVideos.setAdapter(videoAdapter);
            });
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        // Inicializar RecyclerView
        rvListaVideos = findViewById(R.id.rvListaVideos);
        rvListaVideos.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar base de datos
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "usuarios-db").build();

        // Cargar datos de la base de datos
        new Thread(() -> {
            List<Video> videos = db.videoDao().obtenerTodosLosVideos();
            runOnUiThread(() -> {
                // Configurar el adaptador con los datos
                videoAdapter = new VideoAdapter(videos, this, db.videoDao()); // Pasar 'this' como LifecycleOwner
                rvListaVideos.setAdapter(videoAdapter);
            });
        }).start();

        Button btnanadirvideo = findViewById(R.id.btnAgregarVideo);
        btnanadirvideo.setOnClickListener(v -> {
            Intent intent = new Intent(ListaVideosActivity.this, SugerenciasActivity.class);
            startActivity(intent);
        });
    }
}