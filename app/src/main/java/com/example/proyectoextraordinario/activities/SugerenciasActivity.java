package com.example.proyectoextraordinario.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.proyectoextraordinario.R;
import com.example.proyectoextraordinario.adapters.SimpleVideoAdapter;
import com.example.proyectoextraordinario.database.AppDatabase;
import com.example.proyectoextraordinario.models.*;
import com.pierfrancescosoffritti.androidyoutubeplayer.BuildConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Actividad que muestra una lista de videos sugeridos obtenidos desde la API de YouTube.
 * Permite al usuario agregar nuevos videos a la base de datos.
 */
public class SugerenciasActivity extends AppCompatActivity {

    // Clave de la API de YouTube
    private static final String API_KEY = "";

    // RecyclerView para mostrar los videos sugeridos
    private RecyclerView recyclerView;

    // Botón para agregar un nuevo video
    private Button btnAgregarVideo;

    // Adaptador para gestionar los videos en la lista
    private SimpleVideoAdapter videoAdapter;

    // Lista de videos sugeridos
    private List<Video> videoList;

    // Base de datos de la aplicación
    private AppDatabase db;

    /**
     * Método que se ejecuta al crear la actividad.
     * Configura las vistas, inicializa la base de datos y busca videos sugeridos.
     *
     * @param savedInstanceState Estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugerencias);

        // Inicializar la base de datos
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "usuarios-db").build();

        // Inicializar RecyclerView y adaptador
        recyclerView = findViewById(R.id.recyclerViewSugerencias);
        videoList = new ArrayList<>();
        videoAdapter = new SimpleVideoAdapter(videoList, db.videoDao());
        btnAgregarVideo = findViewById(R.id.btnAgregarVideo);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(videoAdapter);

        // Buscar videos sugeridos
        buscarVideos();

        // Configurar el botón para agregar un nuevo video
        btnAgregarVideo.setOnClickListener(v -> {
            Intent intent = new Intent(SugerenciasActivity.this, AnadirVideoActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Método para buscar videos sugeridos desde la API de YouTube.
     * Realiza una búsqueda aleatoria basada en palabras clave.
     */
    private void buscarVideos() {
        // Lista de palabras clave para variar las búsquedas
        String[] keywords = {"programación Android", "desarrollo móvil", "Kotlin", "Java", "apps Android",
                "desarrollador software", "tutorial Android", "API REST", "Firebase", "SQLite Android"};
        String randomQuery = keywords[(int) (Math.random() * keywords.length)]; // Seleccionar una palabra clave aleatoria

        // Crear instancia del servicio de la API de YouTube
        YouTubeApiService apiService = RetrofitClient.getRetrofitInstance().create(YouTubeApiService.class);

        // Realizar la llamada a la API
        Call<YouTubeResponse> call = apiService.buscarVideos(
                "snippet", randomQuery, "video", API_KEY, 10
        );

        call.enqueue(new Callback<YouTubeResponse>() {
            @Override
            public void onResponse(Call<YouTubeResponse> call, Response<YouTubeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    videoList.clear(); // Limpiar la lista antes de agregar nuevos datos
                    for (YouTubeResponse.Item item : response.body().getItems()) {
                        String titulo = item.getSnippet().getTitle(); // Título del video
                        String url = "https://www.youtube.com/watch?v=" + item.getId().getVideoId(); // URL del video
                        String categoria = asignarCategoria(titulo); // Asignar categoría basada en el título

                        // Crear objeto Video y agregarlo a la lista
                        Video video = new Video(titulo, url, categoria, false);
                        videoList.add(video);
                        Log.d("Video", "Título: " + titulo + ", URL: " + url + ", Categoría: " + categoria); // Log para depuración
                    }
                    videoAdapter.notifyDataSetChanged(); // Notificar al adaptador
                } else {
                    Toast.makeText(SugerenciasActivity.this, "Error al obtener videos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<YouTubeResponse> call, Throwable t) {
                Toast.makeText(SugerenciasActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Método para asignar una categoría a un video basado en su título.
     *
     * @param titulo Título del video.
     * @return Categoría asignada.
     */
    private String asignarCategoria(String titulo) {
        String[] categorias = {"Todas", "Programación", "Desarrollo móvil", "Kotlin", "Java", "Apps Android",
                "Desarrollador software", "Tutorial Android", "API REST", "Firebase", "SQLite Android"};
        for (String categoria : categorias) {
            if (titulo.toLowerCase().contains(categoria.toLowerCase())) {
                return categoria;
            }
        }
        return "Sin categoría";
    }
}