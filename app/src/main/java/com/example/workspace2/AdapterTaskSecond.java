package com.example.workspace2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterTaskSecond extends RecyclerView.Adapter<AdapterTaskSecond.SecondTaskViewHolder> {

    private List<Tarea> taskList;
    private OnItemClickListener listener;

    public AdapterTaskSecond(List<Tarea> taskList, OnItemClickListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }

    public Tarea getTaskAtPosition(int position) {
        if (position >= 0 && position < taskList.size()) {
            return taskList.get(position);
        }
        return null;
    }

    @NonNull
    @Override
    public SecondTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_second, parent, false);
        return new SecondTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SecondTaskViewHolder holder, int position) {
        Tarea currentTask = taskList.get(position);

        holder.tituloSecondTxt.setText(currentTask.getNombre());
        holder.fechaSecondTxt.setText(currentTask.getFechaFormateada());

        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class SecondTaskViewHolder extends RecyclerView.ViewHolder {
        TextView tituloSecondTxt;
        TextView fechaSecondTxt;

        public SecondTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloSecondTxt = itemView.findViewById(R.id.tituloSecondTxt);
            fechaSecondTxt = itemView.findViewById(R.id.fechaSecondTxt);
        }
    }

    // Interfaz para manejar clics en elementos del RecyclerView
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}

