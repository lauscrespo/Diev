package com.diev.aplicacion.diev;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.diev.aplicacion.diev.httpclient.HttpConnection;
import com.diev.aplicacion.diev.httpclient.MethodType;
import com.diev.aplicacion.diev.httpclient.RequestConfiguration;
import com.diev.aplicacion.diev.httpclient.StandarRequestConfiguration;
import com.diev.aplicacion.diev.listener.GenericListener;
import com.diev.aplicacion.diev.listener.GeoLocationListener;
import com.diev.aplicacion.diev.object.City;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static com.diev.aplicacion.diev.R.menu.update;

public class CalendarActivity extends AppCompatActivity implements View.OnClickListener {


    private DrawerLayout mDrawerLayout;

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
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_calendar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Set item in checked state
                        menuItem.setChecked(true);
                        if ("COMPARTIR APP".equals(menuItem.getTitle().toString())){
                            shareSocialNetwork();

                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_event);
        fab.setOnClickListener(this);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (locationListener != null)
            locationListener.resume();
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        if (locationListener != null)
            locationListener.start();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Calendar Page",
                Uri.parse("http://host/path"),
               Uri.parse("android-app://com.diev.aplicacion.diev/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Calendar Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.diev.aplicacion.diev/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        if (locationListener != null)
            locationListener.stop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(update, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (locationListener != null)
            locationListener.pause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            Log.i("Menu", "Se abrio el menu con temp");
            cargarWeather();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new MonthContent(), "MONTH");
       viewPager.setAdapter(adapter);
    }


    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {

            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_event) {
            Toast.makeText(this, "Add an Event", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, CrearEvento.class);
            startActivity(intent);
        }
    }

    public void cargarWeather() {

        Log.e("Weather", "Se cargo el Clima");

        ciudad = new City();


        city = (TextView) findViewById(R.id.ciudad);
        tempAct = (TextView) findViewById(R.id.temp);
        country = (TextView) findViewById(R.id.pais);

        geoDetailsContainer = (LinearLayout) findViewById(
                R.id.geo_container);
        //geoDetailsContainer.setVisibility(View.GONE);

        task = new AsyncTask<RequestConfiguration, String, String>() {

            @Override
            protected void onPreExecute() {
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
                    country.setText("BO");
                    city.setText("Santa Cruz de la Sierra");
                    tempAct.setText("1ºC");
                    geoDetailsContainer.setVisibility(View.VISIBLE);
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

                    tempAct.setText(temp + "ºC");
                    CalendarActivity.this.country.setText(country);
                    CalendarActivity.this.city.setText(city);
                    geoDetailsContainer.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
            }
        };

        GenericListener<Location> onLocationReady = new GenericListener<Location>() {
            @Override
            public void action(Location objLocation) {
                currentLocation = objLocation;
                latitud = objLocation.getLatitude();
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

                task.execute(configuration);
            }
        };
        this.locationListener = new GeoLocationListener(this, onLocationReady);
    }

    public void shareSocialNetwork() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_SUBJECT, "Diev");
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.ea.game.tetris2011_row&hl=es-419");
        startActivity(Intent.createChooser(share, "Compartir usando..."));
    }
}


