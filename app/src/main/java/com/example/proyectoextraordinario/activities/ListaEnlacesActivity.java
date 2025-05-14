package com.example.proyectoextraordinario.activities;

import android.os.Bundle;
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
                enlaceAdapter = new EnlaceAdapter(enlaces);
                rvListaEnlaces.setAdapter(enlaceAdapter);
            });
        }).start();
    }
}
