package com.diev.aplicacion.diev;

<<<<<<< HEAD
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diev.aplicacion.diev.R;

import static com.diev.aplicacion.diev.R.layout.fragment_day_content;
import static com.diev.aplicacion.diev.R.layout.fragment_month_content;

public class DayContent extends Fragment {
=======
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.diev.aplicacion.diev.brl.EventoBrl;
import com.diev.aplicacion.diev.model.Evento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.diev.aplicacion.diev.R.menu.update;

public class DayContent extends AppCompatActivity implements MonthLoader.MonthChangeListener,
        WeekView.EventClickListener, WeekView.EventLongPressListener, View.OnClickListener,
        WeekView.EmptyViewLongPressListener {

    private DrawerLayout mDrawerLayout;
    private WeekView mWeekView;
    private FloatingActionButton fab;
    private EventoBrl eventoBrl;

>>>>>>> Luis

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflater.inflate(fragment_day_content, null);

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(fragment_day_content, parent, false));
        }
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 1;

        public ContentAdapter(Context context) {
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // no-op
        }

        @Override
        public int getItemCount() {
            return LENGTH;
=======
        setContentView(R.layout.day_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_calendar);
        setSupportActionBar(toolbar);

        fab = ((FloatingActionButton) findViewById(R.id.btn_new_event));
        fab.setOnClickListener(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        mWeekView = (WeekView) findViewById(R.id.weekView);

        // Set an action when any event is clicked.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        mWeekView.setEmptyViewLongPressListener(this);


        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter(false);

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
                        if ("MES".equals(menuItem.getTitle().toString())) {
                            iniciarMes();
                        }
                        if ("SEMANA".equals(menuItem.getTitle().toString())) {
                            iniciarSemana();
                        }
                        mDrawerLayout.closeDrawers();
                        return true;
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

    public void iniciarMes() {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }

    public void iniciarSemana() {
        Intent intent = new Intent(this, WeekContent.class);
        startActivity(intent);
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
        CrearEvento.setFecha(getEventFecha(time));
        Toast.makeText(this, getEventFecha(time), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Bundle bundle = new Bundle();
        try {
            int numero = (int) event.getId();
            bundle.putInt("ID", numero);
        } catch (NumberFormatException ex) {
            bundle.putInt("ID", 0);
        }
        //Intent nos permite enlazar dos actividades
        ViewEvent.precedencia = "day";
        Intent intent = new Intent(getBaseContext(), ViewEvent.class);
        //añadir parametros
        intent.putExtras(bundle);
        //ejuta intent
        startActivity(intent);

    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        try {
            int numero = (int) event.getId();
            Evento objEvento = new Evento();
            objEvento.setEventoId(numero);
            deleteContact(objEvento);


        } catch (NumberFormatException ex) {
            Log.e("DayContent", ex.getMessage());
        }


    }

    private void deleteContact(final Evento objEvento) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Confirmación");
        dialog.setMessage("¿Está seguro que desea eliminar el Evento seleccionado?");
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
                Intent intent = new Intent(SplashActivity.getInstance(), DayContent.class);
                startActivity(intent);
            }
        });
        dialog.setNegativeButton("No", null);
        dialog.show();
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {

        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        for (int i = 0; i < loadEvento().size(); i++) {
            Evento ev = loadEvento().get(i);


            Calendar startTime = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
            try {
                startTime.setTime(sdf.parse(ev.getFecha() + " " + ev.getHora_ini()));
                startTime.set(Calendar.MONTH, newMonth - 1);
                startTime.set(Calendar.YEAR, newYear);

            } catch (ParseException e) {
                Log.e("DayContent", "Error al cambiar startime");
            }

            Log.e("DayContent", startTime.toString());

            Calendar endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR, 1);
            endTime.set(Calendar.MONTH, newMonth - 1);

            WeekViewEvent event = new WeekViewEvent(ev.getEventoId(), getEventTitle(startTime, ev.getNombre()), startTime, endTime);
            event.setColor(getResources().getColor(R.color.event_color_01));
            events.add(event);
        }
        return events;
    }

    private ArrayList<Evento> loadEvento() {
        ArrayList<Evento> eventos = new ArrayList<>();
        eventoBrl = new EventoBrl(this);
        try {
            eventos = eventoBrl.selectAll();
            Log.d("DayContent", "BD EVento " + eventos.size());
        } catch (Exception e) {
            Log.d(e.getMessage(), "Error de Base Evento al cargar");
            e.printStackTrace();
        }
        Log.d("DayContent", "BD EVento " + eventos.size());
        //ciudadList = (ListView) findViewById(R.id.listView);
        //ciudadList.setAdapter(new eventoAdapter(eventos, this));
        return eventos;
    }


    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     *
     * @param shortDate True if the date values should be short.
     */
    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }

    protected String getEventTitle(Calendar time, String nombre) {
        return String.format(nombre + " of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
    }

    protected String getEventFecha(Calendar time) {
        return String.format("%d/%s/%s", time.get(Calendar.DAY_OF_MONTH), time.get(Calendar.MONTH) + 1, time.get(Calendar.YEAR));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_new_event) {
            if (CrearEvento.fecha.equals("")) {
                Toast.makeText(this, "Seleccione la fecha", Toast.LENGTH_SHORT).show();
            } else {
                CrearEvento.precedencia = "day";
                Intent intent = new Intent(this, CrearEvento.class);
                startActivity(intent);
            }
>>>>>>> Luis
        }
    }
}
