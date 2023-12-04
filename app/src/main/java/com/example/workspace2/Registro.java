package com.example.workspace2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Registro extends AppCompatActivity {

    private EditText etNombre, etEmail, etPassword, etConfirmPassword;
    private Button btncrearperfil, btnbackMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etNombre = findViewById(R.id.ReNombre);
        etEmail = findViewById(R.id.ReEmail);
        etPassword = findViewById(R.id.userpass);
        etConfirmPassword = findViewById(R.id.RePassConfirm);
        btncrearperfil = findViewById(R.id.btncrearperfil);
        btnbackMenu = findViewById(R.id.btnbackMenu);


        //button para guardar los datos en database
        btncrearperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario();
            }
        });

        // button para regresar al Login
        btnbackMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volverAlMenu();
            }
        });
    }

    //metodo para registrar un usuario nuevo
    private void registrarUsuario() {
        String nombre = etNombre.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (validarEntradas(nombre, email, password, confirmPassword)) {
            DataBaseHelper dbHelper = new DataBaseHelper(Registro.this);

            // Validar si el usuario ya existe
            if (!dbHelper.usuarioExiste(nombre, email)) {
                // Si el usuario no existe, se agrega a la base de datos
                Users newUser = new Users(nombre, email, password);

                long userId = dbHelper.agregarUsuario(newUser);

                if (userId != -1) {
                    // Registro exitoso
                    Toast.makeText(Registro.this, "Registrado con exito", Toast.LENGTH_SHORT).show();
                } else {
                    // Error al registrarse
                    Toast.makeText(Registro.this, "Error al registrarse ", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Usuario ya existe
                Toast.makeText(Registro.this, "El usuario ya existe, intentelo nuevamente", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //metodo para Validar que todos los campos esten completos y que las contraseñas coincidan
    private boolean validarEntradas(String nombre, String email, String password, String confirmPassword) {
        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // Método para volver al menú principal
    private void volverAlMenu() {
        Intent intent = new Intent(Registro.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validarContrasena(String password) {

        if (password.length() < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres alfabéticos", Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean hasUpperCase = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
                break;
            }
        }
        if (!hasUpperCase) {
            Toast.makeText(this, "La contraseña debe contener al menos una mayúscula", Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
                break;
            }
        }
        if (!hasDigit) {
            Toast.makeText(this, "La contraseña debe contener al menos un número", Toast.LENGTH_SHORT).show();
            return false;
        }
        String symbols = "!@#$%^&*()-_+=<>?";
        boolean hasSymbol = false;
        for (char c : password.toCharArray()) {
            if (symbols.indexOf(c) != -1) {
                hasSymbol = true;
                break;
            }
        }
        if (!hasSymbol) {
            Toast.makeText(this, "La contraseña debe contener al menos un símbolo", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
