package com.diev.aplicacion.diev;


import android.app.ProgressDialog;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diev.aplicacion.diev.httpclient.HttpConnection;
import com.diev.aplicacion.diev.httpclient.MethodType;
import com.diev.aplicacion.diev.httpclient.StandarRequestConfiguration;
import com.diev.aplicacion.diev.listener.GenericListener;
import com.diev.aplicacion.diev.listener.GeoLocationListener;
import com.diev.aplicacion.diev.object.City;
import com.diev.aplicacion.diev.httpclient.RequestConfiguration;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

public class Weather extends AppCompatActivity {

    private TextView country;
    private TextView tempAct;
    private TextView city;

    private City ciudad;

    private Double latitud;
    private Double longitud;

    private GeoLocationListener locationListener;
    private Location currentLocation;
    private AsyncTask<RequestConfiguration, String, String> task;

    private LinearLayout geoDetailsContainer;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        geoDetailsContainer = (LinearLayout) findViewById(
                R.id.geo_container);
        geoDetailsContainer.setVisibility(View.GONE);

        ciudad = new City();


        city = (TextView) findViewById(R.id.ciudad);
        tempAct = (TextView) findViewById(R.id.temp);
        country = (TextView) findViewById(R.id.pais);


        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading");




        task = new AsyncTask<RequestConfiguration, String, String>() {

            @Override
            protected void onPreExecute() {
                progressDialog.show();
            }

            @Override
            protected String doInBackground(RequestConfiguration... params) {
                RequestConfiguration theConfig = params[0];
                publishProgress("Loading Info");
                String json = null;
                try {
                    json = HttpConnection.sendRequest(theConfig);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return json;
            }

            @Override
            protected void onPostExecute(String result) {
                if (result == null || result.trim().isEmpty()) {
                    country.setText("Error Loading the Information");
                    city.setVisibility(View.GONE);
                    tempAct.setVisibility(View.GONE);

                    geoDetailsContainer.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                    return;
                }

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject main = jsonObject.getJSONObject("main");
                    JSONObject sys = jsonObject.getJSONObject("sys");

                    int id = sys.getInt("id");
                    String city = jsonObject.getString("name");
                    String temp = main.getString("temp");
                    String country = sys.getString("country");

                    ciudad.setCiudadId(id);
                    ciudad.setName(city);
                    ciudad.setTemp(temp);
                    ciudad.setCountry(country);


                    Weather.this.city.setText(city);
                    tempAct.setText(temp + "ºC");
                    Weather.this.country.setText(country);

                    geoDetailsContainer.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressDialog.dismiss();
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);

                progressDialog.setMessage(values[0]);
            }
        };


        GenericListener<Location> onLocationReady = new GenericListener<Location>() {
            @Override
            public void action(Location objLocation) {
                currentLocation = objLocation;
                latitud =  objLocation.getLatitude();
                longitud = objLocation.getLongitude();
                locationListener.stop();
                locationListener = null;


                Hashtable<String, String> params = new Hashtable<>();
                params.put("units", "metric");
                params.put("appid", "3ce8b36f20a07c6ab90a9948d6dc1050");
                params.put("lat", String.valueOf(latitud));
                params.put("lon", String.valueOf(longitud));

                String url = "http://api.openweathermap.org/data/2.5/weather?";

                RequestConfiguration configuration =
                        new StandarRequestConfiguration(url,
                                MethodType.GET, params);

                task.execute(new RequestConfiguration[]{configuration});
            }
        };
        this.locationListener = new GeoLocationListener(this, onLocationReady);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(locationListener != null)
            locationListener.resume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(locationListener != null)
            locationListener.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(locationListener != null)
            locationListener.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(locationListener != null)
            locationListener.pause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
