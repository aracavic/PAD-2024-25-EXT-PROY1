package com.example.proyectoextraordinario.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.proyectoextraordinario.database.AppDatabase;
import com.example.proyectoextraordinario.R;

/**
 * Actividad principal de la aplicación.
 * Es la primera pantalla que se muestra al usuario y permite navegar a la actividad principal.
 */
public class MainActivity extends AppCompatActivity {

    // Instancia de la base de datos de la aplicación
    private AppDatabase db;

    /**
     * Método que se ejecuta al crear la actividad.
     * Configura la vista, inicializa la base de datos y establece el comportamiento del botón.
     *
     * @param savedInstanceState Estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Habilita el diseño Edge-to-Edge para la actividad
        setContentView(R.layout.activity_main);

        // Inicializar la base de datos
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "usuarios-db").build();

        // Configuración de padding para las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configuración del botón para navegar a la actividad principal
        Button button = findViewById(R.id.btnEntrar);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
            startActivity(intent); // Inicia la actividad PrincipalActivity
        });
    }
}