package com.diev.aplicacion.diev.imagenes;


import com.diev.aplicacion.diev.R;

public class Ropa {
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

    public static Ropa[] ITEMS = {
            new Ropa("Jaguar F-Type 2015", R.drawable.login),
            new Ropa("Mercedes AMG-GT", R.drawable.login),
            new Ropa("Mazda MX-5", R.drawable.login),
            new Ropa("Porsche 911 GTS", R.drawable.login),
            new Ropa("BMW Serie 6", R.drawable.login),
            new Ropa("Ford Mondeo", R.drawable.login),
            new Ropa("Volvo V60 Cross Country", R.drawable.login),
            new Ropa("Jaguar XE", R.drawable.login),
            new Ropa("VW Golf R Variant", R.drawable.login),
            new Ropa("Seat León ST Cupra", R.drawable.login),
    };

    /**
     * Obtiene item basado en su identificador
     *
     * @param id identificador
     * @return Coche
     */
    public static Ropa getItem(int id) {
        for (Ropa item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
