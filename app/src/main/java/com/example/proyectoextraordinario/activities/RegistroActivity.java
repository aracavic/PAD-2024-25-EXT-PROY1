package com.example.proyectoextraordinario.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.proyectoextraordinario.database.AppDatabase;
import com.example.proyectoextraordinario.R;
import com.example.proyectoextraordinario.models.Usuario;

public class RegistroActivity extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Configuración de la base de datos con fallbackToDestructiveMigration
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "usuarios-db").build();

        EditText usuarioRegistro = findViewById(R.id.usuarioRegistro);
        EditText emailRegistro = findViewById(R.id.emailRegistro);
        EditText contrasenaRegistro = findViewById(R.id.contrasenaRegistro);
        Button btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(v -> {
            String usuario = usuarioRegistro.getText().toString();
            String email = emailRegistro.getText().toString();
            String contrasena = contrasenaRegistro.getText().toString();

            if (usuario.isEmpty() || email.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(RegistroActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                new Thread(() -> {
                    Usuario usuarioExistente = db.usuarioDao().buscarUsuarioPorNombre(usuario);
                    if (usuarioExistente != null) {
                        runOnUiThread(() -> {
                            Toast.makeText(RegistroActivity.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                        });
                        return;
                    } else {
                        Usuario nuevoUsuario = new Usuario();
                        nuevoUsuario.nombreUsuario = usuario;
                        nuevoUsuario.email = email;
                        nuevoUsuario.contrasena = contrasena;

                        db.usuarioDao().insertarUsuario(nuevoUsuario);

                        runOnUiThread(() -> {
                            Toast.makeText(RegistroActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    }
                }).start();
            }
        });
    }
}
