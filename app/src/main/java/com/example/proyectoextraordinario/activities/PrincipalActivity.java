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

/**
 * Actividad principal que muestra listas de enlaces y videos favoritos.
 * Permite gestionar enlaces y videos, y notifica si las listas están vacías.
 */
public class PrincipalActivity extends AppCompatActivity {

    // RecyclerViews para mostrar enlaces y videos favoritos
    private RecyclerView rvEnlacesFavoritos, rvVideosFavoritos;

    // Adaptadores para gestionar los datos de enlaces y videos
    private EnlaceAdapter enlaceAdapter;
    private VideoAdapter videoAdapter;

    // Base de datos de la aplicación
    private AppDatabase db;

    /**
     * Método que se ejecuta al crear la actividad.
     * Configura las vistas, inicializa la base de datos y carga los datos favoritos.
     *
     * @param savedInstanceState Estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Inicializar RecyclerViews
        rvEnlacesFavoritos = findViewById(R.id.rvEnlacesFavoritos);
        rvVideosFavoritos = findViewById(R.id.rvVideosFavoritos);

        // Configurar diseño lineal para las listas
        rvEnlacesFavoritos.setLayoutManager(new LinearLayoutManager(this));
        rvVideosFavoritos.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar base de datos
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "usuarios-db").build();

        // Solicitar permiso para notificaciones si es necesario
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
            }
        }

        // Cargar enlaces favoritos desde la base de datos
        new Thread(() -> {
            List<Enlace> enlacesFavoritos = db.enlaceDao().obtenerEnlacesFavoritos();
            runOnUiThread(() -> {
                enlaceAdapter = new EnlaceAdapter(enlacesFavoritos, db.enlaceDao());
                rvEnlacesFavoritos.setAdapter(enlaceAdapter);
            });
        }).start();

        // Cargar videos favoritos desde la base de datos
        new Thread(() -> {
            List<Video> videosFavoritos = db.videoDao().obtenerVideosFavoritos();
            runOnUiThread(() -> {
                videoAdapter = new VideoAdapter(videosFavoritos, this, db.videoDao());
                rvVideosFavoritos.setAdapter(videoAdapter);
            });
        }).start();

        // Configurar botón para gestionar enlaces
        Button botonenlaces = findViewById(R.id.btnGestionarEnlaces);
        botonenlaces.setOnClickListener(v -> {
            Intent intent = new Intent(PrincipalActivity.this, ListaEnlacesActivity.class);
            startActivity(intent);
        });

        // Configurar botón para gestionar videos
        Button botonvideos = findViewById(R.id.btnGestionarVideos);
        botonvideos.setOnClickListener(v -> {
            Intent intent = new Intent(PrincipalActivity.this, ListaVideosActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Método que se ejecuta al reanudar la actividad.
     * Recarga los datos favoritos y actualiza las listas.
     */
    @Override
    protected void onResume() {
        super.onResume();

        new Thread(() -> {
            // Obtener listas de favoritos
            List<Enlace> enlacesFavoritos = db.enlaceDao().obtenerEnlacesFavoritos();
            List<Video> videosFavoritos = db.videoDao().obtenerVideosFavoritos();

            runOnUiThread(() -> {
                // Actualizar adaptadores con los datos obtenidos
                enlaceAdapter = new EnlaceAdapter(enlacesFavoritos, db.enlaceDao());
                rvEnlacesFavoritos.setAdapter(enlaceAdapter);

                videoAdapter = new VideoAdapter(videosFavoritos, this, db.videoDao());
                rvVideosFavoritos.setAdapter(videoAdapter);

                // Mostrar notificación si las listas están vacías
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