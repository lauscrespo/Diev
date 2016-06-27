package com.diev.aplicacion.diev.connection;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.diev.aplicacion.diev.R;

import java.io.IOException;
import java.util.StringTokenizer;


public class DbConnection extends SQLiteOpenHelper {
    private static final String NOMBREDB = "/data/data/com.diev.aplicacion.diev/databases/usuario.db";
    private static final int VERSION = 2;
    private static SQLiteDatabase instance;
    private Context currentContext;

    public DbConnection(Context context) {
        super(context, NOMBREDB, null, VERSION);
        this.currentContext = context;
    }

    public static boolean dataBaseExist() {
        SQLiteDatabase checkDB = null;

        try {

            String myPath = NOMBREDB;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {

            //si llegamos aqui es porque la base de datos no existe todavía.

        }
        if (checkDB != null) {

            checkDB.close();

        }
        return checkDB != null;
    }

    public void createDataBase() throws IOException {

        boolean dbExist = dataBaseExist();
        if (!dbExist) {
            executeDDL(R.string.db_v1, this.getWritableDatabase());
        }

    }

    private void executeDDL(int resource, SQLiteDatabase db) {
        Resources res = this.currentContext.getResources();
        StringTokenizer queries = new StringTokenizer(res.getString(resource), ";");
        while (queries.hasMoreTokens()) {
            String q = queries.nextToken();
            db.execSQL(q.replace("\\'", "'"));
        }
    }

    public int insert(Table table, ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();

        return (int) db.insertOrThrow(table.toString(), null, values);
    }

    public void update(Table table, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getWritableDatabase();

        db.update(table.toString(), values, whereClause, whereArgs);
    }

    public void delete(Table table, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(table.toString(), whereClause, whereArgs);
    }

    public Cursor executeQuery(Table table, String[] columns, String whereClause, String[] selectionArgs) {
        SQLiteDatabase db = getReadableDatabase();

        return db.query(table.toString(), columns, whereClause, selectionArgs, null, null, null);
    }

    public Cursor executeQuery(int query, String[] selectionArgs) {
        SQLiteDatabase db = getReadableDatabase();

        String strQuery = this.currentContext.getResources().getString(query);
        return db.rawQuery(strQuery, selectionArgs);
    }

    public void open() throws SQLException {
        //Abre la base de datos
        try {
            createDataBase();
        } catch (IOException e) {
            throw new Error("Ha sido imposible crear la Base de Datos");
        }

        instance = SQLiteDatabase.openDatabase(NOMBREDB, null, SQLiteDatabase.OPEN_READWRITE);

    }

    public SQLiteDatabase get_dbinstance() {
        return instance;
    }

    @Override
    public synchronized void close() {
        if (instance != null)
            instance.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

