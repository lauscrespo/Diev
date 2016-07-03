package com.diev.aplicacion.diev;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.diev.aplicacion.diev.adapter.eventoAdapter;
import com.diev.aplicacion.diev.brl.EventoBrl;
import com.diev.aplicacion.diev.imagenes.Ropa;
import com.diev.aplicacion.diev.model.Evento;

import java.util.ArrayList;

public class ViewEvent extends AppCompatActivity implements View.OnClickListener {

    public static final String ARG_IMBD_ID = "ARG_IMBD_ID";
//    static int id=0;
    private TextView txtNombre;
    private TextView txtDescrip;
    private TextView txtini;
    private TextView txtfin;
    private TextView txtfecha;
    EventoBrl eventoBrl;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_event);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        collapsingToolbar.setTitle(getString(R.string.view_event));

        FloatingActionButton btnGeoLo = (FloatingActionButton) findViewById(R.id.view_sugestion);
        btnGeoLo.setOnClickListener(this);
        txtNombre = (TextView) findViewById(R.id.nombreEvento);
        txtDescrip = (TextView) findViewById(R.id.descripcionEvento);
        txtini = (TextView) findViewById(R.id.horaInicioEvento);
        txtfin = (TextView) findViewById(R.id.horaFinEvento);
        txtfecha = (TextView) findViewById(R.id.fechaEvento);

        try{
            int id = 0;
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if ( bundle != null ) {
                id = bundle.getInt("ID");
                Log.i("ID Evento", id + "");
            }
            if(id > 0){
                loadEvento(id);
            }
        }catch (Exception ex){
            Log.e("Error Al Cargar", ex.toString());
        }

    }
    private void loadEvento(int id){
        Evento evento = new Evento();
        eventoBrl = new EventoBrl(this);
        try {
            evento=eventoBrl.selectById(id);
            txtNombre.setText(evento.getNombre());
            txtDescrip.setText(evento.getDescripcion());
            txtini.setText(evento.getHora_ini());
            txtfin.setText(evento.getHora_fin());
            txtfecha.setText(evento.getFecha());
            Log.d("CalendarView", "BD EVento By ID" + evento.getNombre());
        } catch (Exception e) {
            Log.d(e.getMessage(),"Error de Base Evento al cargar");
            e.printStackTrace();
        }
        Log.d("CalendarView","BD EVento by ID"+evento.getNombre());

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, CalendarActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.view_sugestion) {
            Intent intent = new Intent(this, ViewSuggestion.class);
            startActivity(intent);
        }
    }


}
