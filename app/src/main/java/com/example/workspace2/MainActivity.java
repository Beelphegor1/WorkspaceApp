package com.example.workspace2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterTask.OnItemClickListener {
    private List<Tarea> tareas;
    private AdapterTask adapter;
    private Button btnEditar;
    static final int EDITAR_REQUEST = 1;
    public static MainActivity instance;
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        dbHelper = new DataBaseHelper(this);

        // Se inicializa la lista de tareas
        tareas = new ArrayList<>();

        // Configuración del RecyclerView y su adaptador
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

        // Botón para editar tarea
        btnEditar = findViewById(R.id.btnEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Editar.class);
                Tarea[] arrayTareas = tareas.toArray(new Tarea[tareas.size()]);
                intent.putExtra("ARRAY_TAREAS", arrayTareas);
                startActivityForResult(intent, EDITAR_REQUEST);
            }
        });

        // Notifica al adaptador sobre el cambio en los datos
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(Tarea tarea) {
        Intent intent = new Intent(MainActivity.this, Editar.class);
        intent.putExtra("TAREA_EDITAR", tarea);
        startActivityForResult(intent, EDITAR_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDITAR_REQUEST && resultCode == RESULT_OK && data != null) {
            Tarea tareaEditada = (Tarea) data.getSerializableExtra("TAREA_EDITADA");
            if (tareaEditada != null) {
                // Actualiza la tarea en la lista de tareas
                for (int i = 0; i < tareas.size(); i++) {
                    if (tareas.get(i).getId() == tareaEditada.getId()) {
                        tareas.set(i, tareaEditada);
                        adapter.notifyItemChanged(i);
                        break;
                    }
                }
            }
        }
    }
    public void actualizarListaDeTareas(List<Tarea> nuevaListaDeTareas) {
        tareas.clear();
        tareas.addAll(nuevaListaDeTareas);
        adapter.notifyDataSetChanged();
    }
}
