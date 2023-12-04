package com.example.workspace2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.Serializable;
import java.util.Locale;

public class Tarea implements Serializable {

    private long id;
    private String nombre;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private String Estado;
    private long USER_ID_FK;


    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }


    public Tarea() {
    }
    //clase para Tareas
    public Tarea(String nombre, String descripcion, Date fechaInicio, Date fechaFin) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public long getUSER_ID_FK() {
        return USER_ID_FK;
    }

    public void setUSER_ID_FK(long USER_ID_FK) {
        this.USER_ID_FK = USER_ID_FK;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    //Obtiene la fecha formateada en vez de pasarla de Date a String y al revés
    public String getFechaFormateada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(fechaInicio) + "-" + sdf.format(fechaFin);
    }
}
