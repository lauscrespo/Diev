package com.diev.aplicacion.diev.imagenes;


import android.support.v7.app.AppCompatActivity;


public class Ropa extends AppCompatActivity {
    private String nombre;
    private int idDrawable;


    public Ropa(String nombre, int idDrawable) {

        this.nombre = nombre;
        this.idDrawable = idDrawable;

    }

    public String getNombre() {
        return nombre;
    }


    public int getIdDrawable() {
        return idDrawable;
    }

    public int getId() {
        return nombre.hashCode();
    }

}
