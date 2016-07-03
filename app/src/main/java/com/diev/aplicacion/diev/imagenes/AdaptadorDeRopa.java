package com.diev.aplicacion.diev.imagenes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.diev.aplicacion.diev.R;
import com.diev.aplicacion.diev.SplashActivity;
import com.diev.aplicacion.diev.Weather;
import com.diev.aplicacion.diev.brl.UsuarioBrl;
import com.diev.aplicacion.diev.model.Usuario;

import java.util.ArrayList;

public class AdaptadorDeRopa extends BaseAdapter {
    private Context context;

    private int edad = 0;
    private String sexo = "";
    private String nombre_img = "";
    private int tempera = 0;
    private String estacion = "";
    int cantImagenes = 0;

    public static Ropa[] ITEMS;

    public AdaptadorDeRopa(Context context) {
        this.context = context;
        if (Integer.parseInt(Weather.getTemRopa().trim()) != 0)
            tempera = Integer.parseInt(Weather.getTemRopa().trim());
        else tempera = 25;
        if (tempera < 20) {
            estacion = "invierno";
        }
        if (tempera <= 26 && tempera >= 20) {
            estacion = "otono";
        }
        if (tempera >= 27) {
            estacion = "verano";
        }
        Log.d("Temperatura Ropa", tempera + "");
        //*******consultar tbl Usuario***//
        UsuarioBrl objBrl = new UsuarioBrl(SplashActivity.getInstance());
        try {
            ArrayList<Usuario> usuarios = objBrl.selectAll();
            sexo = usuarios.get(0).getSexo().trim();
            edad = Integer.parseInt(usuarios.get(0).getEdad().trim());
            Log.d("Usuario Registrado", usuarios.get(0).toString());
        } catch (Exception e) {
            Log.e("Ropa", "Error Consulta Usuario " + e.getMessage() + SplashActivity.getInstance());
        }

        if (sexo.equals("Mujer")) {
            if (edad >= 20 && edad < 30) {
                nombre_img = "mujer_20_" + estacion.trim() + "_";
            }
            if (edad >= 30 && edad < 40) {
                nombre_img = "mujer_30_" + estacion.trim() + "_";
            }
            if (edad >= 40 && edad < 50) {
                nombre_img = "mujer_40_" + estacion.trim() + "_";
            }
        }
        if (sexo.equals("Hombre")) {
            if (edad >= 20 && edad < 30) {
                nombre_img = "hombre_20_" + estacion.trim() + "_";
            }
            if (edad >= 30 && edad < 40) {
                nombre_img = "hombre_30_" + estacion.trim() + "_";
            }
            if (edad >= 40 && edad <= 50) {
                nombre_img = "hombre_40_" + estacion.trim() + "_";
            }
        }
       Log.e("Ropa", "nombre imagen " + nombre_img);
        //*****Fin***/////

        if (nombre_img.equals("hombre_20_invierno_")) cantImagenes = 2;
        if (nombre_img.equals("hombre_20_verano_")) cantImagenes = 1;
        if (nombre_img.equals("hombre_20_otono_")) cantImagenes = 2;
        if (nombre_img.equals("hombre_30_invierno_")) cantImagenes = 2;
        if (nombre_img.equals("hombre_30_verano_")) cantImagenes = 1;
        if (nombre_img.equals("hombre_30_otono_")) cantImagenes = 1;
        if (nombre_img.equals("hombre_40_invierno_")) cantImagenes = 1;
        if (nombre_img.equals("hombre_40_verano_")) cantImagenes = 1;
        if (nombre_img.equals("hombre_40_otono_")) cantImagenes = 2;

        if (nombre_img.equals("mujer_20_invierno_")) cantImagenes = 1;
        if (nombre_img.equals("mujer_20_verano_")) cantImagenes = 5;
        if (nombre_img.equals("mujer_20_otono_")) cantImagenes = 3;
        if (nombre_img.equals("mujer_30_invierno_")) cantImagenes = 3;
        if (nombre_img.equals("mujer_30_verano_")) cantImagenes = 4;
        if (nombre_img.equals("mujer_30_otono_")) cantImagenes = 7;
        if (nombre_img.equals("mujer_40_invierno_")) cantImagenes = 4;
        if (nombre_img.equals("mujer_40_verano_")) cantImagenes = 2;
        if (nombre_img.equals("mujer_40_otono_")) cantImagenes = 6;

        ITEMS = new Ropa[cantImagenes];

    }

    @Override
    public int getCount() {
        return ITEMS.length;
    }

    @Override
    public Ropa getItem(int position) {
        return ITEMS[position];
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        for (int i = 0; i < cantImagenes; i++) {
            int nro = i + 1;
            ITEMS[i] = new Ropa("Sugerencia " + nro, SplashActivity.getInstance().getResources().getIdentifier(nombre_img + nro, "drawable", SplashActivity.getInstance().getPackageName()));
        }

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_item, viewGroup, false);
        }

        ImageView imagenRopa = (ImageView) view.findViewById(R.id.imagen_ropa);

        final Ropa item = getItem(position);
        Glide.with(imagenRopa.getContext())
                .load(item.getIdDrawable())
                .into(imagenRopa);

        return view;
    }

    public static Ropa getItemRopa(int id) {

        for (Ropa item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }


}