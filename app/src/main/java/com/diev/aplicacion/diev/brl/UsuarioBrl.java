package com.diev.aplicacion.diev.brl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.diev.aplicacion.diev.connection.DbConnection;
import com.diev.aplicacion.diev.connection.Table;
<<<<<<< HEAD
=======
import com.diev.aplicacion.diev.imagenes.Ropa;
>>>>>>> Luis
import com.diev.aplicacion.diev.model.Usuario;

import java.util.ArrayList;


public class UsuarioBrl {

    private final String USUARIO_ID = "usuarioId";
    private final String NOMBRE = "nombre";
    private final String SEXO = "sexo";
    private final String EDAD = "edad";
    private final String EMAIL = "email";


    protected String[] columns;
    private Context contexto;

    public UsuarioBrl(Context contexto) {
        this.contexto = contexto;
        this.columns = "usuarioId,nombre,sexo,edad, email".split(",");
    }

<<<<<<< HEAD
=======

>>>>>>> Luis
    public int insert(Usuario obj) throws Exception {
        if (obj == null)
            throw new Exception("El objeto ciudad no puede ser nulo");

        if (obj.getNombre() == null || obj.getNombre().trim().isEmpty())
            throw new Exception("El ciudad no puede ser nulo o vacio");

        if (obj.getSexo() == null || obj.getSexo().trim().isEmpty())
            throw new Exception("El ciudad no puede ser nulo o vacio");

        if (obj.getEdad() == null || obj.getEdad().trim().isEmpty())
            throw new Exception("El ciudad no puede ser nulo o vacio");

        if (obj.getEmail() == null || obj.getEmail().trim().isEmpty())
            throw new Exception("El ciudad no puede ser nulo o vacio");


        DbConnection connection = new DbConnection(contexto);
        connection.open();

        ContentValues params = getValuesFromContact(obj);

        return connection.insert(Table.tbl_Usuario, params);
    }

    public void update(Usuario obj) throws Exception {
        if (obj == null)
            throw new Exception("El objeto ciudad no puede ser nulo");

        if (obj.getUsuarioId() <= 0)
            throw new Exception("El id del ciudad no puede ser menor o igual que cero");

        if (obj.getNombre() == null || obj.getNombre().trim().isEmpty())
            throw new Exception("El ciudad no puede ser nulo o vacio");

        if (obj.getSexo() == null || obj.getSexo().trim().isEmpty())
            throw new Exception("El ciudad no puede ser nulo o vacio");

        if (obj.getEdad() == null || obj.getEdad().trim().isEmpty())
            throw new Exception("El ciudad no puede ser nulo o vacio");

        if (obj.getEmail() == null || obj.getEmail().trim().isEmpty())
            throw new Exception("El ciudad no puede ser nulo o vacio");


        DbConnection connection = new DbConnection(contexto);
        connection.open();

        ContentValues params = getValuesFromContact(obj);

        String[] whereParams = new String[]{String.valueOf(obj.getUsuarioId())};
        connection.update(Table.tbl_Usuario, params, " usuarioId = ? ", whereParams);
    }


    public void delete(int ciudadId) throws Exception {
        if (ciudadId <= 0)
            throw new Exception("UsuarioId no puede ser menor o igual que cero");

        DbConnection connection = new DbConnection(contexto);
        connection.open();

        String[] whereParams = new String[]{String.valueOf(ciudadId)};
        connection.delete(Table.tbl_Usuario, " usuarioId = ? ", whereParams);
    }

    public ArrayList<Usuario> selectAll() throws Exception {
        DbConnection connection = new DbConnection(contexto);
        connection.open();

        ArrayList<Usuario> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = connection.executeQuery(Table.tbl_Usuario, columns, null, null);

            while (cursor.moveToNext()) {
                Usuario obj = getUsuarioFromCursor(cursor);
                list.add(obj);
            }
        } finally {
            if (cursor != null)
                cursor.close();

            connection.close();
        }
        return list;
    }

    public Usuario selectById(int ciudadId) throws Exception {
        DbConnection connection = new DbConnection(contexto);
        connection.open();

        Cursor cursor = null;
        Usuario obj = null;
        try {
            cursor = connection.executeQuery(Table.tbl_Usuario, columns,
                    "usuarioId = ? ", new String[]{String.valueOf(ciudadId)});

            if (cursor.moveToFirst()) {
                obj = getUsuarioFromCursor(cursor);

            }
        } finally {
            if (cursor != null)
                cursor.close();

            connection.close();
        }
        return obj;
    }

    private Usuario getUsuarioFromCursor(Cursor cursor) {
        Usuario obj = new Usuario();
        obj.setUsuarioId(cursor.getInt(cursor.getColumnIndex(USUARIO_ID)));
        obj.setNombre(cursor.getString(cursor.getColumnIndex(NOMBRE)));
        obj.setSexo(cursor.getString(cursor.getColumnIndex(SEXO)));
        obj.setEdad(cursor.getString(cursor.getColumnIndex(EDAD)));
        obj.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
        return obj;
    }

    private ContentValues getValuesFromContact(Usuario obj) {
        ContentValues params = new ContentValues();
        if (obj.getUsuarioId() > 0)
            params.put(USUARIO_ID, obj.getUsuarioId());
        params.put(NOMBRE, obj.getNombre());
        params.put(SEXO, obj.getSexo());
        params.put(EDAD, obj.getEdad());
        params.put(EMAIL, obj.getEmail());

        return params;
    }


}
