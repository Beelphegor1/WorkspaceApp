package com.example.workspace2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AdapterTask extends RecyclerView.Adapter<AdapterTask.ViewHolder> {
    private List<Tarea> tareas;

    public AdapterTask(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el diseño del elemento de tarea
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Llenar los datos en las vistas del ViewHolder
        Tarea tarea = tareas.get(position);
        holder.tituloTextView.setText(tarea.getNombre());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fechaInicioStr = sdf.format(tarea.getFechaInicio());
        String fechaFinStr = sdf.format(tarea.getFechaFin());
        holder.fechaTextView.setText(fechaInicioStr + "-" + fechaFinStr);
        holder.descripcionTextView.setText(tarea.getDescripcion());
    }

    @Override
    public int getItemCount() {
        // Retorna la cantidad de elementos en la lista
        return tareas.size();
    }

    // ViewHolder y otros métodos del adaptador...

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tituloTextView;
        TextView fechaTextView;
        TextView descripcionTextView;

        ViewHolder(View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.titulomaintxt);
            fechaTextView = itemView.findViewById(R.id.fechamaintxt);
            descripcionTextView = itemView.findViewById(R.id.descripcionmaintxt);
        }
    }
}
