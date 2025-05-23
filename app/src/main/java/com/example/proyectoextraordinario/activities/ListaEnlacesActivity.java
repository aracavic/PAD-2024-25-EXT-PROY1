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

public class ListaEnlacesActivity extends AppCompatActivity {

    private RecyclerView rvListaEnlaces;
    private EnlaceAdapter enlaceAdapter;
    private AppDatabase db;
    private Spinner spinnerTematicas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlace);

        Button btnAgregarEnlace = findViewById(R.id.btnAgregarEnlace);

        // Inicializar RecyclerView
        rvListaEnlaces = findViewById(R.id.rvListaEnlaces);
        rvListaEnlaces.setLayoutManager(new LinearLayoutManager(this));
        spinnerTematicas = findViewById(R.id.spinnerTematicas);

        // Inicializar base de datos
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "usuarios-db").build();

        Spinner spinnerTematicas = findViewById(R.id.spinnerTematicas);
        String[] tematicas = {"Todas","Inteligencia Artificial","Ciencia de Datos", "Ciberseguridad", "Desarrollo Web", "Diseño UX/UI", "Cloud Computing", "DevOps", "Software Libre"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tematicas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTematicas.setAdapter(adapter);

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

        // Cargar datos de la base de datos
        new Thread(() -> {
            List<Enlace> enlaces = db.enlaceDao().obtenerTodosLosEnlaces();
            runOnUiThread(() -> {
                // Configurar el adaptador con los datos
                enlaceAdapter = new EnlaceAdapter(enlaces,db.enlaceDao());
                rvListaEnlaces.setAdapter(enlaceAdapter);
            });
        }).start();

        btnAgregarEnlace.setOnClickListener(v -> {
            Intent intent = new Intent(ListaEnlacesActivity.this, AnadirEnlaceActivity.class);
            startActivity(intent);
        });


    }

    private void filtrarEnlacesPorTematica(String tematica) {
        new Thread(() -> {
            List<Enlace> enlaces;
            if (tematica.equals("Todas")) {
                enlaces = db.enlaceDao().obtenerTodosLosEnlaces();
            } else {
                enlaces = db.enlaceDao().obtenerEnlacesPorCategoria(tematica);
            }
            runOnUiThread(() -> {
                enlaceAdapter = new EnlaceAdapter(enlaces, db.enlaceDao());
                rvListaEnlaces.setAdapter(enlaceAdapter);
            });
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar la lista de enlaces al volver a esta actividad
        new Thread(() -> {
            List<Enlace> enlaces = db.enlaceDao().obtenerTodosLosEnlaces();
            runOnUiThread(() -> {
                enlaceAdapter = new EnlaceAdapter(enlaces,db.enlaceDao());
                rvListaEnlaces.setAdapter(enlaceAdapter);
            });
        }).start();
    }
}
