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
            descripcion = (TextView) view.findViewById(R.id.txtDescripcion);
            imagen = (ImageView) view.findViewById(R.id.iv_clock);
        }


    }

    //descargar imagen
    private Bitmap DownloadImage(String imageHttpAddress) {
        URL imageUrl = null;
        Bitmap imagen = null;
        try {
            imageUrl = new URL(imageHttpAddress);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            imagen = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return imagen;
    }

    //metodo asincrono que descarga la imagen
    class DownloadFileFromURL extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            Log.i("doInBackground ", "Entra en doInBackground");
            String url = params[0];
            Bitmap imagen = DownloadImage(url);
            img = imagen;
            return imagen;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            img = result;
        }

    }
}
