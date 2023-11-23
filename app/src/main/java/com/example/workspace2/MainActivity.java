package com.example.workspace2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterTask.OnItemClickListener{

    private List<Tarea> tareas;
    private AdapterTask adapter;
    private Button btnEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Se inicializa la lista de tareas
        tareas = new ArrayList<>();

        // Configuracion del Recycleview y su adaptador
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterTask(tareas, this); // Usar la variable de instancia 'adapter'
        recyclerView.setAdapter(adapter);


        // Botón para agregar tarea
        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abre la actividad para agregar tarea
                Intent intent = new Intent(MainActivity.this, Agregar.class);
                startActivity(intent);
            }
        });
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Editar.class);

                // Convierte la lista de tareas a un array y pasa el array
                Tarea[] arrayTareas = tareas.toArray(new Tarea[tareas.size()]);
                intent.putExtra("ARRAY_TAREAS", arrayTareas);

                startActivity(intent);
            }
        });

        // Agregar tareas de ejemplo (puedes omitir esto si obtienes las tareas de tu base de datos)
        tareas.add(new Tarea("Tarea 1", "Descripción 1", new Date(), new Date()));
        tareas.add(new Tarea("Tarea 2", "Descripción 2", new Date(), new Date()));
        tareas.add(new Tarea("Tarea 3", "Descripción 3", new Date(), new Date()));

        // Notificar al adaptador sobre el cambio en los datos
        adapter.notifyDataSetChanged();


    }

    @Override
    public void onItemClick(Tarea tarea) {
        Intent intent = new Intent(MainActivity.this, Editar.class);
        intent.putExtra("TAREA_EDITAR", tarea);
        startActivity(intent);
    }
}
