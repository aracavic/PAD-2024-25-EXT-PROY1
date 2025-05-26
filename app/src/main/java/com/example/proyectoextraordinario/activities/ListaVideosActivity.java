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

/**
 * Actividad que muestra una lista de videos almacenados en la base de datos.
 * Permite filtrar los videos por temática y agregar nuevos videos.
 */
public class ListaVideosActivity extends AppCompatActivity {

    // RecyclerView para mostrar la lista de videos
    private RecyclerView rvListaVideos;

    // Adaptador para gestionar los datos de los videos
    private VideoAdapter videoAdapter;

    // Base de datos de la aplicación
    private AppDatabase db;

    /**
     * Método que se ejecuta al reanudar la actividad.
     * Recarga los datos de la base de datos y actualiza la lista de videos.
     */
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

    /**
     * Método que se ejecuta al crear la actividad.
     * Inicializa las vistas, configura el RecyclerView, el spinner de temáticas y el botón para agregar videos.
     *
     * @param savedInstanceState Estado guardado de la actividad.
     */
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
        String[] tematicas = {"Todas", "Programación", "Desarrollo móvil", "Kotlin", "Java", "Apps Android",
                "Desarrollador software", "Tutorial Android", "API REST", "Firebase", "SQLite Android"};
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

        // Configurar botón para agregar un nuevo video
        Button btnanadirvideo = findViewById(R.id.btnAgregarVideo);
        btnanadirvideo.setOnClickListener(v -> {
            Intent intent = new Intent(ListaVideosActivity.this, SugerenciasActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Filtra los videos por la temática seleccionada en el spinner.
     *
     * @param tematica Temática seleccionada para filtrar los videos.
     */
    private void filtrarVideosPorTematica(String tematica) {
        new Thread(() -> {
            List<Video> videos;
            if (tematica.equals("Todas")) {
                // Obtener todos los videos si se selecciona "Todas"
                videos = db.videoDao().obtenerTodosLosVideos();
            } else {
                // Obtener videos filtrados por la categoría seleccionada
                videos = db.videoDao().obtenerVideosPorCategoria(tematica);
            }
            runOnUiThread(() -> {
                // Actualizar el adaptador con los datos filtrados
                videoAdapter = new VideoAdapter(videos, this, db.videoDao());
                rvListaVideos.setAdapter(videoAdapter);
            });
        }).start();
    }
}