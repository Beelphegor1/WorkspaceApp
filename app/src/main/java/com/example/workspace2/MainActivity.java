package com.example.workspace2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Tarea> tareas;
    private AdapterTask adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar la lista de tareas (puedes obtenerla de tu base de datos u otro origen)
        tareas = new ArrayList<>();

        // Configurar RecyclerView y su adaptador
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterTask(tareas);
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

        // Botón para editar tarea (puedes implementar la lógica según tus necesidades)
        Button btnEditar = findViewById(R.id.btnEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abre la actividad para editar tarea
                Intent intent = new Intent(MainActivity.this, Editar.class);
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
}
