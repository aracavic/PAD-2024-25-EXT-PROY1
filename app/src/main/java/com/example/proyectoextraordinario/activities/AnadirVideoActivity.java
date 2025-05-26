package com.example.proyectoextraordinario.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.proyectoextraordinario.R;
import com.example.proyectoextraordinario.database.AppDatabase;
import com.example.proyectoextraordinario.models.Video;

/**
 * Actividad para añadir un nuevo video.
 * Permite al usuario ingresar un título, URL, categoría y guardar el video en la base de datos.
 */
public class AnadirVideoActivity extends AppCompatActivity {

    // Campos de entrada para el título y la URL del video
    private EditText etTituloVideo, etUrlVideo;

    // Botón para guardar el video
    private Button btnGuardarVideo;

    // Base de datos de la aplicación
    private AppDatabase db;

    // Spinner para seleccionar la temática del video
    private Spinner spinnerTematicas;

    /**
     * Método que se ejecuta al crear la actividad.
     * Inicializa las vistas, configura el spinner de temáticas y el botón de guardar.
     *
     * @param savedInstanceState Estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_video);

        // Inicializar vistas
        etTituloVideo = findViewById(R.id.etTituloVideo);
        etUrlVideo = findViewById(R.id.etUrlVideo);
        spinnerTematicas = findViewById(R.id.spinnerTematicas);
        btnGuardarVideo = findViewById(R.id.btnGuardarVideo);

        // Inicializar base de datos
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "usuarios-db").build();

        // Configurar el spinner de temáticas con una lista de opciones
        String[] tematicas = {"Programación", "Desarrollo móvil", "Kotlin", "Java", "apps Android",
                "desarrollador software", "tutorial Android", "API REST", "Firebase", "SQLite Android"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tematicas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTematicas.setAdapter(adapter);

        // Configurar el botón para guardar el video
        btnGuardarVideo.setOnClickListener(v -> {
            // Obtener los valores ingresados por el usuario
            String titulo = etTituloVideo.getText().toString().trim();
            String url = etUrlVideo.getText().toString().trim();
            String categoria = spinnerTematicas.getSelectedItem().toString();

            // Validar que todos los campos estén completos
            if (titulo.isEmpty() || url.isEmpty() || categoria.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Guardar el video en la base de datos en un hilo separado
            new Thread(() -> {
                // Crear un objeto Video con los datos ingresados
                Video video = new Video(titulo, url, categoria, false);

                // Insertar el video en la base de datos
                db.videoDao().insertarVideo(video);

                // Mostrar un mensaje de éxito en el hilo principal
                runOnUiThread(() -> {
                    Toast.makeText(this, "Video guardado exitosamente", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }).start();
        });
    }
}