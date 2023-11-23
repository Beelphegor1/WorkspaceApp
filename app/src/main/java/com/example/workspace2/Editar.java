package com.example.workspace2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class Editar extends AppCompatActivity implements AdapterTask.OnItemClickListener {
    private Button btnback1;
    private List<Tarea> tareas;
    private AdapterTask adapter;
    private int selectedTaskPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        btnback1 = findViewById(R.id.btnback1);

        // Obtén la lista de tareas del Intent
        Intent intent = getIntent();
        if (intent.hasExtra("ARRAY_TAREAS")) {
            Tarea[] arrayTareas = (Tarea[]) intent.getSerializableExtra("ARRAY_TAREAS");
            // Convierte el array de nuevo a una lista
            tareas = Arrays.asList(arrayTareas);
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerViewEditar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (tareas != null) {
            adapter = new AdapterTask(tareas, this);
            recyclerView.setAdapter(adapter);
        }

        btnback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Editar.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button btnSaveChanges = findViewById(R.id.btnSaveChanges);
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Guarda los cambios en la tarea seleccionada
                guardarCambiosEnTarea();
            }
        });
    }

    private void guardarCambiosEnTarea() {
        if (selectedTaskPosition != RecyclerView.NO_POSITION) {
            // Obtener la tarea seleccionada
            Tarea tareaSeleccionada = tareas.get(selectedTaskPosition);

            // Actualizar los datos de la tarea con la nueva descripción y estado
            EditText newDescriptionEditText = findViewById(R.id.newdescriptiontxt);
            String nuevaDescripcion = newDescriptionEditText.getText().toString();
            tareaSeleccionada.setDescripcion(nuevaDescripcion);

            Spinner estadoSpinner = findViewById(R.id.Estado);
            String nuevoEstado = estadoSpinner.getSelectedItem().toString();
            tareaSeleccionada.setEstado(nuevoEstado);

            // Notificar al adaptador sobre el cambio en los datos
            adapter.notifyItemChanged(selectedTaskPosition);
        }
    }

    public void onItemClick(int position) {
        // Al hacer clic en un elemento, actualizar la interfaz con los datos de la tarea seleccionada
        selectedTaskPosition = position;

        // Obtener la tarea seleccionada
        Tarea tareaSeleccionada = tareas.get(position);

        // Rellenar los elementos de la interfaz con los datos de la tarea seleccionada
        TextView tituloTextView = findViewById(R.id.Titulotxt);
        TextView fechaTextView = findViewById(R.id.fechatxt);
        TextView descripcionTextView = findViewById(R.id.descripciontxt);
        tituloTextView.setText(tareaSeleccionada.getNombre());
        fechaTextView.setText(tareaSeleccionada.getFechaFormateada());
        descripcionTextView.setText(tareaSeleccionada.getDescripcion());

        // Habilitar la edición en los campos relevantes
        EditText newDescriptionEditText = findViewById(R.id.newdescriptiontxt);
        newDescriptionEditText.setText(tareaSeleccionada.getDescripcion());

        // Seleccionar el estado actual en el spinner
        Spinner estadoSpinner = findViewById(R.id.Estado);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Estado, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estadoSpinner.setAdapter(adapter);

// Obtener el estado actual de la tarea
        String estadoActual = tareaSeleccionada.getEstado();

// Seleccionar el estado actual en el spinner
        int estadoActualPosition = adapter.getPosition(estadoActual);
        estadoSpinner.setSelection(estadoActualPosition);

}

    @Override
    public void onItemClick(Tarea tarea) {

    }
}
