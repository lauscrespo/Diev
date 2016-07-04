package com.diev.aplicacion.diev.brl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.diev.aplicacion.diev.connection.DbConnection2;
import com.diev.aplicacion.diev.connection.Table2;
import com.diev.aplicacion.diev.model.Evento;

import java.util.ArrayList;


public class EventoBrl {

    private final String EVENTO_ID = "eventoId";
    private final String NOMBRE = "nombre";
    private final String DESCRIPCION = "descripcion";
    private final String HORA_INI = "hora_ini";
    private final String HORA_FIN = "hora_fin";
    private final String FECHA = "fecha";


    protected String[] columns;
    private Context contexto;

    public EventoBrl(Context contexto) {
        this.contexto = contexto;
        this.columns = "eventoId,nombre,descripcion,hora_ini, hora_fin, fecha".split(",");
    }

    public int insert(Evento obj) throws Exception {
        if (obj == null)
            throw new Exception("El objeto ciudad no puede ser nulo");

        if (obj.getNombre() == null || obj.getNombre().trim().isEmpty())
            throw new Exception("El ciudad no puede ser nulo o vacio");

        if (obj.getDescripcion() == null || obj.getDescripcion().trim().isEmpty())
            throw new Exception("El ciudad no puede ser nulo o vacio");

        if (obj.getHora_ini() == null || obj.getHora_ini().trim().isEmpty())
            throw new Exception("El ciudad no puede ser nulo o vacio");

        if (obj.getHora_fin() == null || obj.getHora_fin().trim().isEmpty())
            throw new Exception("El ciudad no puede ser nulo o vacio");

        if (obj.getFecha() == null || obj.getFecha().trim().isEmpty())
            throw new Exception("El ciudad no puede ser nulo o vacio");


        DbConnection2 connection = new DbConnection2(contexto);
        connection.open();

        ContentValues params = getValuesFromContact(obj);

        return connection.insert(Table2.tbl_Evento, params);
    }

    public void update(Evento obj) throws Exception {
        if (obj == null)
            throw new Exception("El objeto ciudad no puede ser nulo");

        if (obj.getEventoId() <= 0)
            throw new Exception("El id del ciudad no puede ser menor o igual que cero");

        if (obj.getNombre() == null || obj.getNombre().trim().isEmpty())
            throw new Exception("El ciudad no puede ser nulo o vacio");

        if (obj.getDescripcion() == null || obj.getDescripcion().trim().isEmpty())
            throw new Exception("El ciudad no puede ser nulo o vacio");

        if (obj.getHora_ini() == null || obj.getHora_ini().trim().isEmpty())
            throw new Exception("El ciudad no puede ser nulo o vacio");

        if (obj.getHora_fin() == null || obj.getHora_fin().trim().isEmpty())
            throw new Exception("El ciudad no puede ser nulo o vacio");

        if (obj.getFecha() == null || obj.getFecha().trim().isEmpty())
            throw new Exception("El ciudad no puede ser nulo o vacio");


        DbConnection2 connection = new DbConnection2(contexto);
        connection.open();

        ContentValues params = getValuesFromContact(obj);

        String[] whereParams = new String[]{String.valueOf(obj.getEventoId())};
        connection.update(Table2.tbl_Evento, params, " eventoId = ? ", whereParams);
    }


    public void delete(int ciudadId) throws Exception {
        if (ciudadId <= 0)
            throw new Exception("EventoId no puede ser menor o igual que cero");

        DbConnection2 connection = new DbConnection2(contexto);
        connection.open();

        String[] whereParams = new String[]{String.valueOf(ciudadId)};
        connection.delete(Table2.tbl_Evento, " eventoId = ? ", whereParams);
    }

    public ArrayList<Evento> selectAll() throws Exception {
        DbConnection2 connection = new DbConnection2(contexto);
        connection.open();

        ArrayList<Evento> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = connection.executeQuery(Table2.tbl_Evento, columns, null, null);

            while (cursor.moveToNext()) {
                Evento obj = getEventoFromCursor(cursor);
                list.add(obj);
            }
        } finally {
            if (cursor != null)
                cursor.close();

            connection.close();
        }
        return list;
    }

    public Evento selectById(int ciudadId) throws Exception {
        DbConnection2 connection = new DbConnection2(contexto);
        connection.open();

        Cursor cursor = null;
        Evento obj = null;
        try {
            cursor = connection.executeQuery(Table2.tbl_Evento, columns,
                    "eventoId = ? ", new String[]{String.valueOf(ciudadId)});

            if (cursor.moveToFirst()) {
                obj = getEventoFromCursor(cursor);

            }
        } finally {
            if (cursor != null)
                cursor.close();

            connection.close();
        }
        return obj;
    }

    public ArrayList<Evento> selectByFecha(String fecha) throws Exception {
        DbConnection2 connection = new DbConnection2(contexto);
        connection.open();

        ArrayList<Evento> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = connection.executeQuery(Table2.tbl_Evento, columns,
                    "fecha = ? ", new String[]{fecha});

            while (cursor.moveToNext()) {
                Evento obj = getEventoFromCursor(cursor);
                list.add(obj);
            }
        } finally {
            if (cursor != null)
                cursor.close();

            connection.close();
        }
        return list;
    }

    private Evento getEventoFromCursor(Cursor cursor) {
        Evento obj = new Evento();
        obj.setEventoId(cursor.getInt(cursor.getColumnIndex(EVENTO_ID)));
        obj.setNombre(cursor.getString(cursor.getColumnIndex(NOMBRE)));
        obj.setDescripcion(cursor.getString(cursor.getColumnIndex(DESCRIPCION)));
        obj.setHora_ini(cursor.getString(cursor.getColumnIndex(HORA_INI)));
        obj.setHora_fin(cursor.getString(cursor.getColumnIndex(HORA_FIN)));
        obj.setFecha(cursor.getString(cursor.getColumnIndex(FECHA)));
        return obj;
    }

    private ContentValues getValuesFromContact(Evento obj) {
        ContentValues params = new ContentValues();
        if (obj.getEventoId() > 0)
            params.put(EVENTO_ID, obj.getEventoId());
        params.put(NOMBRE, obj.getNombre());
        params.put(DESCRIPCION, obj.getDescripcion());
        params.put(HORA_INI, obj.getHora_ini());
        params.put(HORA_FIN, obj.getHora_fin());
        params.put(FECHA, obj.getFecha());
        return params;
    }


}
