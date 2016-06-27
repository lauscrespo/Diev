package com.diev.aplicacion.diev.imagenes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.diev.aplicacion.diev.R;

public class AdaptadorDeRopa extends BaseAdapter {
    private Context context;

    public AdaptadorDeRopa(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Ropa.ITEMS.length;
    }

    @Override
    public Ropa getItem(int position) {
        return Ropa.ITEMS[position];
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_item, viewGroup, false);
        }

        ImageView imagenCoche = (ImageView) view.findViewById(R.id.imagen_ropa);
        TextView nombreCoche = (TextView) view.findViewById(R.id.nombre_ropa);

        final Ropa item = getItem(position);
        Glide.with(imagenCoche.getContext())
                .load(item.getIdDrawable())
                .into(imagenCoche);

        nombreCoche.setText(item.getNombre());

        return view;
    }

}