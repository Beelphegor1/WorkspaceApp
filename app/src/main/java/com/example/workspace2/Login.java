package com.example.workspace2;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText usertxt;
    private EditText userpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usertxt = findViewById(R.id.usertxt);
        userpass = findViewById(R.id.userpass);

        Button btningresar = findViewById(R.id.btningresar);
        btningresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usertxt.getText().toString();
                String password = userpass.getText().toString();

                // Lógica de autenticación
                if (autenticarUsuario(username, password)) {
                    // Autenticación exitosa
                    long userId = obtenerUserIdPorNombreUsuario(username);

                    // Guarda el ID del usuario en UserManager o en otra clase de utilidad
                    UserManager.setUserId(userId);

                    // Inicia la actividad principal
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Cierra la actividad de inicio de sesión para que el usuario no pueda volver atrás
                } else {
                    // Autenticación fallida
                    Toast.makeText(Login.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnregistro = findViewById(R.id.btnregistro);
        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
            }
        });
    }

    private boolean autenticarUsuario(String username, String password) {
        DataBaseHelper dbHelper = new DataBaseHelper(Login.this);
        return dbHelper.verificarCredenciales(username, password);
    }

    private long obtenerUserIdPorNombreUsuario(String username) {
        DataBaseHelper dbHelper = new DataBaseHelper(Login.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        long userId = -1; // Valor predeterminado si no se encuentra el usuario

        // Consulta para obtener el ID del usuario según el nombre de usuario
        Cursor cursor = db.rawQuery("SELECT " + dbHelper.getColumnUserId() +
                " FROM " + dbHelper.getTableUsers() +
                " WHERE " + dbHelper.getColumnUserName() + " = ?", new String[]{username});

        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getLong(cursor.getColumnIndexOrThrow(dbHelper.getColumnUserId()));
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return userId;
    }




}


