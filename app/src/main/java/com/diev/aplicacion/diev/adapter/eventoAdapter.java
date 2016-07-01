package com.diev.aplicacion.diev.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.diev.aplicacion.diev.R;
import com.diev.aplicacion.diev.model.Evento;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class eventoAdapter extends BaseAdapter {

    private List<Evento> eventos;
    private Context context;
    private Bitmap img;

    public eventoAdapter(List<Evento> eventos, Context context) {
        this.eventos = eventos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return eventos.size();
    }

    @Override
    public Object getItem(int position) {

        return eventos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return eventos.get(position).getEventoId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Evento obj = eventos.get(position);

        viewHolder.nombre.setText(obj.getNombre());
        viewHolder.descripcion.setText(obj.getDescripcion());
        return convertView;
    }


    class ViewHolder {
        TextView nombre;
        TextView descripcion;
        ImageView imagen;

        ViewHolder(View view) {
            nombre = (TextView) view.findViewById(R.id.txt_eNombre);
            descripcion = (TextView) view.findViewById(R.id.txt_eDescrip);
            imagen = (ImageView) view.findViewById(R.id.iv_clock);
        }


    }


}
