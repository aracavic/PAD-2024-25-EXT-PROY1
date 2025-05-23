package com.example.proyectoextraordinario.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.proyectoextraordinario.R;
import com.example.proyectoextraordinario.adapters.EnlaceAdapter;
import com.example.proyectoextraordinario.adapters.VideoAdapter;
import com.example.proyectoextraordinario.database.AppDatabase;
import com.example.proyectoextraordinario.models.Enlace;
import com.example.proyectoextraordinario.models.Video;

import java.util.List;

public class PrincipalActivity extends AppCompatActivity {

    private RecyclerView rvEnlacesFavoritos, rvVideosFavoritos;
    private EnlaceAdapter enlaceAdapter;
    private VideoAdapter videoAdapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Inicializar RecyclerViews
        rvEnlacesFavoritos = findViewById(R.id.rvEnlacesFavoritos);
        rvVideosFavoritos = findViewById(R.id.rvVideosFavoritos);

        rvEnlacesFavoritos.setLayoutManager(new LinearLayoutManager(this));
        rvVideosFavoritos.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar base de datos
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "usuarios-db").build();

        // Cargar enlaces favoritos
        new Thread(() -> {
            List<Enlace> enlacesFavoritos = db.enlaceDao().obtenerEnlacesFavoritos();
            runOnUiThread(() -> {
                enlaceAdapter = new EnlaceAdapter(enlacesFavoritos);
                rvEnlacesFavoritos.setAdapter(enlaceAdapter);
            });
        }).start();

        // Cargar videos favoritos
        new Thread(() -> {
            List<Video> videosFavoritos = db.videoDao().obtenerVideosFavoritos();
            runOnUiThread(() -> {
                videoAdapter = new VideoAdapter(videosFavoritos, this, db.videoDao());
                rvVideosFavoritos.setAdapter(videoAdapter);
            });
        }).start();

        // Botón para gestionar enlaces
        Button botonenlaces = findViewById(R.id.btnGestionarEnlaces);
        botonenlaces.setOnClickListener(v -> {
            Intent intent = new Intent(PrincipalActivity.this, ListaEnlacesActivity.class);
            startActivity(intent);
        });

        // Botón para gestionar videos
        Button botonvideos = findViewById(R.id.btnGestionarVideos);
        botonvideos.setOnClickListener(v -> {
            Intent intent = new Intent(PrincipalActivity.this, ListaVideosActivity.class);
            startActivity(intent);
        });
    }
}