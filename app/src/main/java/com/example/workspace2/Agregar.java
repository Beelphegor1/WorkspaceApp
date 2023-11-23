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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
        tvStartDate = findViewById(R.id.Fini); // Agregado
        tvEndDate = findViewById(R.id.Ffinal); // Agregado
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        final DataBaseHelper dbHelper = new DataBaseHelper(this);

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

                Tarea tarea = new Tarea();
                tarea.setNombre(taskName);
                tarea.setDescripcion(taskDescription);
                // Agregar lógica para establecer el usuario (userId) asociado a la tarea
                dbHelper.agregarTarea(tarea, userId);
            }
        });
    }

    private void showDatePickerDialog(final boolean isStartDate) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Obtener la fecha seleccionada
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                Date selectedDate = calendar.getTime();

                // Formatear la fecha y mostrarla en el TextView correspondiente
                String formattedDate = dateFormatter.format(selectedDate);
                if (isStartDate) {
                    tvStartDate.setText(formattedDate);
                } else {
                    tvEndDate.setText(formattedDate);
                }
            }
        };

        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Mostrar el diálogo de selección de fecha
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.show();
    }
}



