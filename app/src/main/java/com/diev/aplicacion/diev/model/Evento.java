package com.diev.aplicacion.diev.model;

public class Evento {

    private int eventoId;
    private String nombre;
    private String descripcion;
    private String hora_ini;
    private String hora_fin;


    public Evento(int eventoId, String nombre, String descripcion, String hora_ini, String hora_fin) {
        this.eventoId = eventoId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.hora_ini = hora_ini;
        this.hora_fin = hora_fin;

    }

    public Evento() {

    }

    public int getEventoId() {
        return eventoId;
    }

    public void setEventoId(int eventoId) {
        this.eventoId = eventoId;
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

    public String getHora_ini() {
        return hora_ini;
    }

    public void setHora_ini(String hora_ini) {
        this.hora_ini = hora_ini;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }


}
