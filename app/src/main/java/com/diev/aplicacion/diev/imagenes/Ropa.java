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
            new Ropa("Clothes", R.drawable.login),
            new Ropa("Clothes", R.drawable.login),
            new Ropa("Clothes", R.drawable.login),
            new Ropa("Clothes", R.drawable.login),
            new Ropa("Clothes", R.drawable.login),
            new Ropa("Clothes", R.drawable.login),
            new Ropa("Clothes", R.drawable.login),
            new Ropa("Clothes", R.drawable.login),
            new Ropa("Clothes", R.drawable.login),
            new Ropa("Clothes", R.drawable.login),
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
