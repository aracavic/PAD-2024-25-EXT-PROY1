package com.example.proyectoextraordinario.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.proyectoextraordinario.R;
import com.example.proyectoextraordinario.database.AppDatabase;
import com.example.proyectoextraordinario.models.*;

/**
 * Actividad para añadir un nuevo enlace.
 * Permite al usuario ingresar un título, URL, categoría y marcarlo como favorito.
 */
public class AnadirEnlaceActivity extends AppCompatActivity {

    private EditText etTitulo, etUrl, etCategoria;
    private CheckBox cbFavorito;
    private Button btnGuardar;
    private AppDatabase db;
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
        setContentView(R.layout.activity_anadir_enlace);

        // Inicializar la base de datos
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "usuarios-db").build();

        // Inicializar vistas
        etTitulo = findViewById(R.id.etTitulo);
        etUrl = findViewById(R.id.etUrl);
        cbFavorito = findViewById(R.id.cbFavorito);
        btnGuardar = findViewById(R.id.btnGuardar);
        spinnerTematicas = findViewById(R.id.spinnerTematicas);

        // Configurar el spinner de temáticas
        String[] tematicas = {"Inteligencia Artificial", "Ciencia de Datos", "Ciberseguridad",
                "Desarrollo Web", "Diseño UX/UI", "Cloud Computing", "DevOps", "Software Libre"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tematicas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTematicas.setAdapter(adapter);

        // Configurar el botón de guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarEnlace();
            }
        });
    }

    /**
     * Método para guardar un nuevo enlace en la base de datos.
     * Valida los campos antes de guardar y muestra un mensaje de confirmación.
     */
    private void guardarEnlace() {
        // Obtener datos de los campos
        String titulo = etTitulo.getText().toString().trim();
        String url = etUrl.getText().toString().trim();
        String categoria = spinnerTematicas.getSelectedItem().toString();
        boolean favorito = cbFavorito.isChecked();

        // Validar campos
        if (titulo.isEmpty() || url.isEmpty() || categoria.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Guardar el enlace en la base de datos en un hilo separado
        new Thread(() -> {
            // Crear objeto Enlace
            Enlace enla = new Enlace(titulo, url, categoria, favorito);

            // Insertar el enlace en la base de datos
            db.enlaceDao().insertarEnlace(enla);

            // Mostrar mensaje de éxito en el hilo principal
            runOnUiThread(() -> {
                Toast.makeText(this, "Video guardado exitosamente", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();

        // Finalizar la actividad
        finish();
    }
}