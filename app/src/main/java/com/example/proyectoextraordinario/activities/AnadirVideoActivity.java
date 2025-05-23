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

public class AnadirVideoActivity extends AppCompatActivity {

    private EditText etTituloVideo, etUrlVideo;
    private Button btnGuardarVideo;
    private AppDatabase db;
    private Spinner spinnerTematicas;

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

        Spinner spinnerTematicas = findViewById(R.id.spinnerTematicas);
        String[] tematicas = {"Programación", "Desarrollo móvil", "Kotlin", "Java", "apps Android", "desarrollador software", "tutorial Android", "API REST", "Firebase", "SQLite Android"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tematicas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTematicas.setAdapter(adapter);

        // Configurar botón para guardar el video
        btnGuardarVideo.setOnClickListener(v -> {
            String titulo = etTituloVideo.getText().toString().trim();
            String url = etUrlVideo.getText().toString().trim();
            String categoria = spinnerTematicas.getSelectedItem().toString();

            if (titulo.isEmpty() || url.isEmpty() || categoria.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Guardar el video en la base de datos
            new Thread(() -> {
                Video video = new Video(titulo, url, categoria, false);
                db.videoDao().insertarVideo(video);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Video guardado exitosamente", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }).start();
        });
    }
}
