package com.example.proyectoextraordinario.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.proyectoextraordinario.database.AppDatabase;
import com.example.proyectoextraordinario.models.Enlace;
import com.example.proyectoextraordinario.adapters.EnlaceAdapter;
import com.example.proyectoextraordinario.R;

import java.util.List;

public class ListaEnlacesActivity extends AppCompatActivity {

    private RecyclerView rvListaEnlaces;
    private EnlaceAdapter enlaceAdapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlace);

        Button btnAgregarEnlace = findViewById(R.id.btnAgregarEnlace);

        // Inicializar RecyclerView
        rvListaEnlaces = findViewById(R.id.rvListaEnlaces);
        rvListaEnlaces.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar base de datos
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "usuarios-db").build();

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
