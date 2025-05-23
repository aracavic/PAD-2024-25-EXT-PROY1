package com.example.proyectoextraordinario.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

import javax.xml.transform.OutputKeys;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
            }
        }


        // Cargar enlaces favoritos
        new Thread(() -> {
            List<Enlace> enlacesFavoritos = db.enlaceDao().obtenerEnlacesFavoritos();
            runOnUiThread(() -> {
                enlaceAdapter = new EnlaceAdapter(enlacesFavoritos, db.enlaceDao());
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

    @Override
    protected void onResume() {
        super.onResume();

        new Thread(() -> {
            List<Enlace> enlacesFavoritos = db.enlaceDao().obtenerEnlacesFavoritos();
            List<Video> videosFavoritos = db.videoDao().obtenerVideosFavoritos();

            runOnUiThread(() -> {
                // Actualizar adaptadores
                enlaceAdapter = new EnlaceAdapter(enlacesFavoritos, db.enlaceDao());
                rvEnlacesFavoritos.setAdapter(enlaceAdapter);

                videoAdapter = new VideoAdapter(videosFavoritos, this, db.videoDao());
                rvVideosFavoritos.setAdapter(videoAdapter);

                // Verificar listas vacías y notificar
                if (enlacesFavoritos.isEmpty() || videosFavoritos.isEmpty()) {
                    com.example.proyectoextraordinario.utils.NotificacionUtil.mostrarNotificacion(
                            this,
                            "Favoritos vacíos",
                            "Tu lista de enlaces o videos favoritos está vacía."
                    );
                }
            });
        }).start();
    }

}