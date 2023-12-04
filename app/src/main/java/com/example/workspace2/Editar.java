package com.example.workspace2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Editar extends AppCompatActivity {
    private Button btnback1, btnSaveChanges, btnDeleteTask;
    private RecyclerView recyclerView;
    private AdapterTaskSecond adapter;
    private List<Tarea> tareas;
    private int selectedTaskPosition = RecyclerView.NO_POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        btnback1 = findViewById(R.id.btnback1);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        btnDeleteTask = findViewById(R.id.btnDeleteTask);

        recyclerView = findViewById(R.id.recyclerViewEditar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        if (intent.hasExtra("ARRAY_TAREAS")) {
            Tarea[] arrayTareas = (Tarea[]) intent.getSerializableExtra("ARRAY_TAREAS");
            tareas = new ArrayList<>(Arrays.asList(arrayTareas));
        } else {
            tareas = new ArrayList<>();
        }

        adapter = new AdapterTaskSecond(tareas, new AdapterTaskSecond.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Manejar la selección de la tarea en el RecyclerView
                handleTaskSelection(position);
            }
        });

        recyclerView.setAdapter(adapter);

        btnback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Editar.this, MainActivity.class);
                startActivity(intent1);
            }
        });

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarCambiosEnTarea();
            }
        });

        btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarTareaSeleccionada();
            }
        });
    }

    private void handleTaskSelection(int position) {
        selectedTaskPosition = position;
    }

    private void guardarCambiosEnTarea() {
        if (selectedTaskPosition != RecyclerView.NO_POSITION) {
            Tarea tareaSeleccionada = tareas.get(selectedTaskPosition);

            EditText newDescriptionEditText = findViewById(R.id.newdescriptiontxt);
            String nuevaDescripcion = newDescriptionEditText.getText().toString();
            tareaSeleccionada.setDescripcion(nuevaDescripcion);

            Spinner estadoSpinner = findViewById(R.id.Estado);
            String nuevoEstado = estadoSpinner.getSelectedItem().toString();
            tareaSeleccionada.setEstado(nuevoEstado);

            // Actualiza la tarea en la lista
            tareas.set(selectedTaskPosition, tareaSeleccionada);

            // Notifica al adaptador sobre el cambio
            adapter.notifyItemChanged(selectedTaskPosition);
        }
    }

    private void eliminarTareaSeleccionada() {
        if (selectedTaskPosition != RecyclerView.NO_POSITION) {
            // Elimina la tarea de la lista
            tareas.remove(selectedTaskPosition);

            // Notifica al adaptador sobre la eliminación
            adapter.notifyItemRemoved(selectedTaskPosition);

            // Restablece la posición seleccionada
            selectedTaskPosition = RecyclerView.NO_POSITION;
        }
    }
}
