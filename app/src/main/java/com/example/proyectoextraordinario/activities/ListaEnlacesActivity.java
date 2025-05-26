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

import com.example.proyectoextraordinario.adapters.VideoAdapter;
import com.example.proyectoextraordinario.database.AppDatabase;
import com.example.proyectoextraordinario.models.Enlace;
import com.example.proyectoextraordinario.adapters.EnlaceAdapter;
import com.example.proyectoextraordinario.R;
import com.example.proyectoextraordinario.models.Video;

import java.util.List;

/**
 * Actividad que muestra una lista de enlaces almacenados en la base de datos.
 * Permite filtrar los enlaces por temática y agregar nuevos enlaces.
 */
public class ListaEnlacesActivity extends AppCompatActivity {

    // RecyclerView para mostrar la lista de enlaces
    private RecyclerView rvListaEnlaces;

    // Adaptador para gestionar los datos de los enlaces
    private EnlaceAdapter enlaceAdapter;

    // Base de datos de la aplicación
    private AppDatabase db;

    // Spinner para seleccionar la temática de los enlaces
    private Spinner spinnerTematicas;

    /**
     * Método que se ejecuta al crear la actividad.
     * Inicializa las vistas, configura el RecyclerView, el spinner de temáticas y el botón para agregar enlaces.
     *
     * @param savedInstanceState Estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlace);

        // Botón para agregar un nuevo enlace
        Button btnAgregarEnlace = findViewById(R.id.btnAgregarEnlace);

        // Inicializar RecyclerView
        rvListaEnlaces = findViewById(R.id.rvListaEnlaces);
        rvListaEnlaces.setLayoutManager(new LinearLayoutManager(this));
        spinnerTematicas = findViewById(R.id.spinnerTematicas);

        // Inicializar base de datos
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "usuarios-db").build();

        // Configurar el spinner de temáticas con una lista de opciones
        String[] tematicas = {"Todas", "Inteligencia Artificial", "Ciencia de Datos", "Ciberseguridad",
                "Desarrollo Web", "Diseño UX/UI", "Cloud Computing", "DevOps", "Software Libre"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tematicas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTematicas.setAdapter(adapter);

        // Configurar el listener del spinner para filtrar enlaces por temática
        spinnerTematicas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tematicaSeleccionada = tematicas[position];
                filtrarEnlacesPorTematica(tematicaSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });

        // Cargar datos de la base de datos y configurar el adaptador
        new Thread(() -> {
            List<Enlace> enlaces = db.enlaceDao().obtenerTodosLosEnlaces();
            runOnUiThread(() -> {
                // Configurar el adaptador con los datos obtenidos
                enlaceAdapter = new EnlaceAdapter(enlaces, db.enlaceDao());
                rvListaEnlaces.setAdapter(enlaceAdapter);
            });
        }).start();

        // Configurar el botón para agregar un nuevo enlace
        btnAgregarEnlace.setOnClickListener(v -> {
            Intent intent = new Intent(ListaEnlacesActivity.this, AnadirEnlaceActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Filtra los enlaces por la temática seleccionada en el spinner.
     *
     * @param tematica Temática seleccionada para filtrar los enlaces.
     */
    private void filtrarEnlacesPorTematica(String tematica) {
        new Thread(() -> {
            List<Enlace> enlaces;
            if (tematica.equals("Todas")) {
                // Obtener todos los enlaces si se selecciona "Todas"
                enlaces = db.enlaceDao().obtenerTodosLosEnlaces();
            } else {
                // Obtener enlaces filtrados por la categoría seleccionada
                enlaces = db.enlaceDao().obtenerEnlacesPorCategoria(tematica);
            }
            runOnUiThread(() -> {
                // Actualizar el adaptador con los datos filtrados
                enlaceAdapter = new EnlaceAdapter(enlaces, db.enlaceDao());
                rvListaEnlaces.setAdapter(enlaceAdapter);
            });
        }).start();
    }

    /**
     * Método que se ejecuta al reanudar la actividad.
     * Actualiza la lista de enlaces para reflejar los cambios realizados.
     */
    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar la lista de enlaces al volver a esta actividad
        new Thread(() -> {
            List<Enlace> enlaces = db.enlaceDao().obtenerTodosLosEnlaces();
            runOnUiThread(() -> {
                enlaceAdapter = new EnlaceAdapter(enlaces, db.enlaceDao());
                rvListaEnlaces.setAdapter(enlaceAdapter);
            });
        }).start();
    }
}