package com.example.proyectoextraordinario.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoextraordinario.R;

public class PrincipalActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


        Button botonenlaces = findViewById(R.id.btnGestionarEnlaces);
        botonenlaces.setOnClickListener(v -> {
            Intent intent = new Intent(PrincipalActivity.this, ListaEnlacesActivity.class);
            startActivity(intent);
        });

        Button botonvideos = findViewById(R.id.btnGestionarVideos);
        botonvideos.setOnClickListener(v -> {
            Intent intent = new Intent(PrincipalActivity.this, ListaVideosActivity.class);
            startActivity(intent);
        });
    }


}
