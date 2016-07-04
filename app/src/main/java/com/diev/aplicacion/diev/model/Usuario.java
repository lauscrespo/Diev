package com.diev.aplicacion.diev.model;

public class Usuario {

    private int usuarioId;
    private String nombre;
    private String sexo;
    private String edad;
    private String email;


    public Usuario(int usuarioId, String nombre, String sexo, String edad, String email) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.sexo = sexo;
        this.edad = edad;
        this.email = email;

    }

    public Usuario() {

    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
