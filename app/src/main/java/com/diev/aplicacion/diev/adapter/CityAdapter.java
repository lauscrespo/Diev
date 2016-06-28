package com.diev.aplicacion.diev.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.diev.aplicacion.diev.R;
import com.diev.aplicacion.diev.object.City;

import java.util.List;

public class CityAdapter extends BaseAdapter {
    private List<City> citiesList;
    private Context context;

    public CityAdapter(List<City> cities, Context context) {
        this.citiesList = cities;
        this.context = context;
    }

    @Override
    public int getCount() {
        return citiesList.size();
    }

    @Override
    public Object getItem(int position) {
        return citiesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.nav_header, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        City objCity = citiesList.get(position);
        viewHolder.country.setText("Pais: " + objCity.getCountry());
        viewHolder.temp.setText("Temperatura: " + objCity.getTemp() + "ºC");
        viewHolder.name.setText(objCity.getName());

        return convertView;
    }

    class ViewHolder {
        TextView country;
        TextView temp;
        TextView name;

        public ViewHolder(View v) {
            name = (TextView) v.findViewById(R.id.ciudad);
            country = (TextView) v.findViewById(R.id.pais);
            temp = (TextView) v.findViewById(R.id.temp);
        }
    }

}
