package com.example.workspace2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;

public class Agregar extends AppCompatActivity {
    private EditText taskNameEditText;
    private EditText taskDescriptionEditText;
    private Button btnStartDate;
    private Button btnEndDate;
    private Button btnBack;
    private Button btnAgregar;
    private TextView tvStartDate;
    private TextView tvEndDate;
    private SimpleDateFormat dateFormatter;
    private long userId;
    private AdapterTask adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        taskNameEditText = findViewById(R.id.TaskName);
        taskDescriptionEditText = findViewById(R.id.TaskDescription);
        btnStartDate = findViewById(R.id.FechaI);
        btnEndDate = findViewById(R.id.FechaF);
        btnBack = findViewById(R.id.btnBack);
        btnAgregar = findViewById(R.id.bttnAgregar);
        tvStartDate = findViewById(R.id.Fini);
        tvEndDate = findViewById(R.id.Ffinal);
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        final DataBaseHelper dbHelper = new DataBaseHelper(this);
        adapter = new AdapterTask(new ArrayList<>());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Agregar.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(true);
            }
        });

        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(false);
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = taskNameEditText.getText().toString();
                String taskDescription = taskDescriptionEditText.getText().toString();

                if (!taskName.isEmpty() && !taskDescription.isEmpty()) {
                    // Establece el usuario asociado a la tarea
                    long userId = Users.getUserId();

                    Tarea tarea = new Tarea();
                    tarea.setNombre(taskName);
                    tarea.setDescripcion(taskDescription);

                    // Agregar lógica para establecer el usuario (userId) asociado a la tarea
                    long tareaId = dbHelper.agregarTarea(tarea, userId);

                    if (tareaId != -1) {
                        // La tarea se agregó correctamente
                        Toast.makeText(Agregar.this, "Tarea agregada con éxito", Toast.LENGTH_SHORT).show();

                        // Obtén la instancia de MainActivity y actualiza directamente el adaptador
                        if (MainActivity.instance != null) {
                            MainActivity.instance.actualizarListaDeTareas(dbHelper.obtenerTodasLasTareas());
                        }

                        // Limpiar los campos después de guardar
                        limpiarCampos();
                    } else {
                        // Error al agregar la tarea
                        Toast.makeText(Agregar.this, "Error al agregar la tarea", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Agregar.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                }
            }



        private void limpiarCampos() {
                taskNameEditText.setText("");
                taskDescriptionEditText.setText("");
                tvStartDate.setText("");
                tvEndDate.setText("");
            }
        });

    }

    private void showDatePickerDialog(final boolean isStartDate) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Obtiene la fecha seleccionada
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                Date selectedDate = calendar.getTime();

                // Formatea la fecha y la muestra en el TextView
                String formattedDate = dateFormatter.format(selectedDate);
                if (isStartDate) {
                    tvStartDate.setText(formattedDate);
                } else {
                    tvEndDate.setText(formattedDate);
                }
            }
        };

        // Obtiene la fecha actual
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.show();
    }
}
