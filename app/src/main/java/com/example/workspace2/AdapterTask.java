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
    private final List<Tarea> tareas;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(Tarea tarea);
    }

    public AdapterTask(List<Tarea> tareas, OnItemClickListener listener) {
        this.tareas = tareas;
        this.listener = listener;
    }

    @NonNull
    @Override
    //infla la vista
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tarea tarea = tareas.get(position);
        holder.tituloTextView.setText(tarea.getNombre());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fechaInicioStr = sdf.format(tarea.getFechaInicio());
        String fechaFinStr = sdf.format(tarea.getFechaFin());
        holder.fechaTextView.setText(fechaInicioStr + "-" + fechaFinStr);
        holder.descripcionTextView.setText(tarea.getDescripcion());
        holder.estadoTextView.setText(tarea.getEstado());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(tarea);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tareas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tituloTextView;
        TextView fechaTextView;
        TextView descripcionTextView;
        TextView estadoTextView;

        ViewHolder(View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.titulomaintxt);
            fechaTextView = itemView.findViewById(R.id.fechamaintxt);
            descripcionTextView = itemView.findViewById(R.id.descripcionmaintxt);
            estadoTextView = itemView.findViewById(R.id.estadomaintxt);
        }
    }
}
