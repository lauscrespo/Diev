package com.diev.aplicacion.diev;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;


import com.diev.aplicacion.diev.brl.EventoBrl;
import com.diev.aplicacion.diev.model.Evento;
import com.diev.aplicacion.diev.model.Usuario;

import java.util.Calendar;

public class CrearEvento extends Activity implements
        View.OnClickListener {


    private static String fecha;

    EventoBrl objBrl;
    private int eventoId;
    private EditText txtNombre;
    private EditText txtDescripcion;
    ImageButton btnTimePicker;
    EditText  txtTime;
    ImageButton  btnTimePicker2;
    EditText  txtTime2;
    private int mHour, mMinute;
    private int mHour2, mMinute2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);
        objBrl = new EventoBrl(CrearEvento.this);

        btnTimePicker = (ImageButton) findViewById(R.id.btnTimePicker);
        txtTime = (EditText) findViewById(R.id.txtTime);
        btnTimePicker.setOnClickListener(this);

        btnTimePicker2 = (ImageButton) findViewById(R.id.btnTimePicker2);
        txtTime2 = (EditText) findViewById(R.id.txtTime2);
        btnTimePicker2.setOnClickListener(this);

        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);

        Button btnSave = (Button) findViewById(R.id.btn_saveEvent);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEvento();
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == btnTimePicker) {

            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog tpd = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            tpd.show();
        }

        if (v == btnTimePicker2) {

            final Calendar c = Calendar.getInstance();
            mHour2 = c.get(Calendar.HOUR_OF_DAY);
            mMinute2 = c.get(Calendar.MINUTE);

            TimePickerDialog tpd = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            txtTime2.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour2, mMinute2, false);
            tpd.show();
        }
    }
    ///evento que guarda el evento
    private void saveEvento() {
        String fecha = getFecha();
        String nombre = txtNombre.getText().toString();
        String descripcion = txtDescripcion.getText().toString();
        String horaIni = txtTime.getText().toString();
        String horaFin = txtTime2.getText().toString();

        if (fecha.trim().isEmpty()) {
            Toast.makeText(this, "Ingrese la FECHA", Toast.LENGTH_SHORT).show();
            return;
        }

        if (nombre.trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el nombre", Toast.LENGTH_SHORT).show();
            return;
        }
        if (descripcion.trim().isEmpty()) {
            Toast.makeText(this, "Ingrese la descripcion", Toast.LENGTH_SHORT).show();
            return;
        }
        if (horaIni.trim().isEmpty()) {
            Toast.makeText(this, "Ingrese la hora inicial", Toast.LENGTH_SHORT).show();
            return;
        }
        if (horaFin.trim().isEmpty()) {
            Toast.makeText(this, "Ingrese la hora final", Toast.LENGTH_SHORT).show();
            return;
        }
        String result = nombre.replace(" ", "");
        Evento evento = new Evento();

        //llenar datos evento
        evento.setFecha(fecha);
        evento.setEventoId(0);
        evento.setNombre(nombre);
        evento.setDescripcion(descripcion);
        evento.setHora_ini(horaIni);
        evento.setHora_fin(horaFin);

        try {
            objBrl.insert(evento);
            Toast.makeText(this, "Evento Registrado", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e("MainActivity", "error al insertar evento");
        }
    }

    public static String getFecha() {
        return fecha;
    }

    public static void setFecha(String fecha) {
        CrearEvento.fecha = fecha;
    }
}