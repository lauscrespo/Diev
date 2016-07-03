package com.diev.aplicacion.diev;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.diev.aplicacion.diev.adapter.eventoAdapter;
import com.diev.aplicacion.diev.brl.EventoBrl;
import com.diev.aplicacion.diev.model.Evento;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import static com.diev.aplicacion.diev.R.menu.update;

public class CalendarActivity extends AppCompatActivity implements View.OnClickListener {


    private DrawerLayout mDrawerLayout;
    private FloatingActionButton fab;
    private ListView eventsList;
    private EventoBrl eventoBrl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view);
        ArrayList<Evento> eventos = new ArrayList<>();
        eventoBrl = new EventoBrl(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_calendar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        HashSet<Date> events = new HashSet<>();
        events.add(new Date());

        CalendarView cv = ((CalendarView) findViewById(R.id.calendar_view));
        cv.updateCalendar(events);

        fab = ((FloatingActionButton) findViewById(R.id.btn_new_event));
        fab.setOnClickListener(this);


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
                        if ("COMPARTIR APP".equals(menuItem.getTitle().toString())) {
                            shareSocialNetwork();
                        }
                        if ("CLIMA".equals(menuItem.getTitle().toString())) {
                            iniciarWeather();
                        }
                        if ("SEMANA".equals(menuItem.getTitle().toString())) {
                            iniciarSemana();
                        }
                        if ("DIA".equals(menuItem.getTitle().toString())) {
                            iniciarDia();
                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        loadEvento();
        registerForContextMenu(eventsList);
    }

    public void loadEvento() {
        ArrayList<Evento> eventos = new ArrayList<>();
        eventoBrl = new EventoBrl(this);
        try {
            eventos = eventoBrl.selectAll();
            Log.d("CalendarView", "BD EVento " + eventos.size());
        } catch (Exception e) {
            Log.d(e.getMessage(), "Error de Base Evento al cargar");
            e.printStackTrace();
        }
        Log.d("CalendarView", "BD EVento " + eventos.size());
        eventsList = (ListView) findViewById(R.id.listView);
        eventsList.setAdapter(new eventoAdapter(eventos, this));
        eventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,long arg3) {
                Evento eventoSeleccionado = (Evento)adapter.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                try
                {
                    int numero =  eventoSeleccionado.getEventoId();
                    bundle.putInt( "ID" , numero );
                }
                catch ( NumberFormatException ex ){
                    bundle.putInt( "ID" , 0 );
                }
                //Intent nos permite enlazar dos actividades
                Intent intent = new Intent(getBaseContext(), ViewEvent.class);
                //añadir parametros
                intent.putExtras( bundle );
                //ejuta intent
                startActivity( intent );
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
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
    }

    //mantener pulsado para eliminar
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_evento, menu);

    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();

        if (info == null || info.position < 0)
            return true;

        int itemPosition = info.position;

        switch (item.getItemId()) {
            case R.id.action_delete_evento:
                Evento objEvento = (Evento) eventsList.getAdapter().getItem(itemPosition);
                deleteContact(objEvento);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_update) {
            Intent intent = new Intent(this, Weather.class);
            startActivity(intent);
            return true;
        } else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    public void shareSocialNetwork() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_SUBJECT, "Diev");
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.ea.game.tetris2011_row&hl=es-419");
        startActivity(Intent.createChooser(share, "Compartir usando..."));
    }

    public void iniciarWeather() {
        Intent intent = new Intent(this, Weather.class);
        startActivity(intent);
    }

    public void iniciarSemana() {
        Intent intent = new Intent(this, WeekContent.class);
        startActivity(intent);
    }

    public void iniciarDia() {
        Intent intent = new Intent(this, DayContent.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_new_event) {
            if (CrearEvento.fecha.equals("")) {
                Toast.makeText(this, "Seleccione la fecha", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, CrearEvento.class);
                startActivity(intent);
            }
        }
    }


    private void deleteContact(final Evento objEvento) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Confirmación");
        dialog.setMessage("¿Está seguro que desea eliminar el Evento seleccionada?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int boton) {
                try {

                    eventoBrl.delete(objEvento.getEventoId());
                } catch (Exception e) {
                    Toast.makeText(SplashActivity.getInstance(), "Error al eliminar el evento", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(SplashActivity.getInstance(), "Evento eliminado", Toast.LENGTH_SHORT).show();
                loadEvento();
            }
        });
        dialog.setNegativeButton("No", null);
        dialog.show();
    }
}


