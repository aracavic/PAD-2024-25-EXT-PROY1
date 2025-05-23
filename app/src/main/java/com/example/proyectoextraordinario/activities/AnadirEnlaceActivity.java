package com.example.proyectoextraordinario.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.proyectoextraordinario.R;
import com.example.proyectoextraordinario.database.AppDatabase;
import com.example.proyectoextraordinario.models.*;


public class AnadirEnlaceActivity extends AppCompatActivity {

    private EditText etTitulo, etUrl, etCategoria;
    private CheckBox cbFavorito;
    private Button btnGuardar;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_enlace);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "usuarios-db").build();

        // Inicializar vistas
        etTitulo = findViewById(R.id.etTitulo);
        etUrl = findViewById(R.id.etUrl);
        etCategoria = findViewById(R.id.etCategoria);
        cbFavorito = findViewById(R.id.cbFavorito);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Configurar botón de guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarEnlace();
            }
        });
    }

    private void guardarEnlace() {
        // Obtener datos de los campos
        String titulo = etTitulo.getText().toString().trim();
        String url = etUrl.getText().toString().trim();
        String categoria = etCategoria.getText().toString().trim();
        boolean favorito = cbFavorito.isChecked();

        // Validar campos
        if (titulo.isEmpty() || url.isEmpty() || categoria.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }



        new Thread(() -> {
            // Crear objeto Enlace
            Enlace enla = new Enlace(titulo, url, categoria, favorito);

            db.enlaceDao().insertarEnlace(enla);
            runOnUiThread(() -> {
                Toast.makeText(this, "Video guardado exitosamente", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();

        finish();
    }

}
