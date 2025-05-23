package com.example.proyectoextraordinario.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.proyectoextraordinario.database.AppDatabase;
import com.example.proyectoextraordinario.R;
import com.example.proyectoextraordinario.models.Video;
import com.example.proyectoextraordinario.adapters.VideoAdapter;

import java.util.List;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

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
                videoAdapter = new VideoAdapter(videos, this, db.videoDao());
                rvListaVideos.setAdapter(videoAdapter);
            });
        }).start();

        // Configurar Spinner
        Spinner spinnerTematicas = findViewById(R.id.spinnerTematicas);
        String[] tematicas = {"Todas","Programación", "Desarrollo móvil", "Kotlin", "Java", "apps Android", "desarrollador software", "tutorial Android", "API REST", "Firebase", "SQLite Android"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tematicas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTematicas.setAdapter(adapter);

        // Manejar selección del Spinner
        spinnerTematicas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tematicaSeleccionada = tematicas[position];
                filtrarVideosPorTematica(tematicaSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });

        Button btnanadirvideo = findViewById(R.id.btnAgregarVideo);
        btnanadirvideo.setOnClickListener(v -> {
            Intent intent = new Intent(ListaVideosActivity.this, SugerenciasActivity.class);
            startActivity(intent);
        });
    }

    // Método para filtrar videos por temática
    private void filtrarVideosPorTematica(String tematica) {
        new Thread(() -> {
            List<Video> videos;
            if (tematica.equals("Todas")) {
                videos = db.videoDao().obtenerTodosLosVideos();
            } else {
                videos = db.videoDao().obtenerVideosPorCategoria(tematica);
            }
            runOnUiThread(() -> {
                videoAdapter = new VideoAdapter(videos, this, db.videoDao());
                rvListaVideos.setAdapter(videoAdapter);
            });
        }).start();
    }

}