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

public class AdapterTask extends RecyclerView.Adapter<AdapterTask.TaskViewHolder> {

    private List<Tarea> taskList;

    public AdapterTask(List<Tarea> taskList) {
        this.taskList = taskList;
    }
    public interface OnItemClickListener {
        void onItemClick(Tarea tarea);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Tarea currentTask = taskList.get(position);

        holder.tituloMainTxt.setText(currentTask.getNombre());
        holder.fechaMainTxt.setText(currentTask.getFechaFormateada());
        holder.descripcionMainTxt.setText(currentTask.getDescripcion());
        holder.estadoMainTxt.setText(currentTask.getEstado());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tituloMainTxt;
        TextView fechaMainTxt;
        TextView descripcionMainTxt;
        TextView estadoMainTxt;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloMainTxt = itemView.findViewById(R.id.titulomaintxt);
            fechaMainTxt = itemView.findViewById(R.id.fechamaintxt);
            descripcionMainTxt = itemView.findViewById(R.id.descripcionmaintxt);
            estadoMainTxt = itemView.findViewById(R.id.estadomaintxt);
        }
    }
}


