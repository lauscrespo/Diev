package com.diev.aplicacion.diev;

import android.app.Activity;
import android.content.Intent;
<<<<<<< HEAD
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;

import com.diev.aplicacion.diev.brl.UsuarioBrl;
import com.diev.aplicacion.diev.model.Usuario;
=======
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.diev.aplicacion.diev.brl.UsuarioBrl;
import com.diev.aplicacion.diev.model.Usuario;
import com.diev.aplicacion.diev.tools.Constantes;
import com.diev.aplicacion.diev.web.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
>>>>>>> Luis

import java.util.ArrayList;


public class SplashActivity extends Activity {

<<<<<<< HEAD
    // Duración en milisegundos que se mostrará el splash
    private final int DURACION_SPLASH = 3000; // 3 segundos
=======
    /*
    Etiqueta de depuracion
     */
    private static final String TAG = SplashActivity.class.getSimpleName();

    private Gson gson = new Gson();

    // Duración en milisegundos que se mostrará el splash
    private final int DURACION_SPLASH = 3000; // 3 segundos
    private static SplashActivity myContext;

    private UsuarioBrl objBrl;

    public SplashActivity() {
        myContext = this;
    }

    public static SplashActivity getInstance() {
        return myContext;
    }
>>>>>>> Luis

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD

        // Tenemos una plantilla llamada splash.xml donde mostraremos la información que queramos (logotipo, etc.)
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable(){
            public void run(){
                // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación

                leer();
                //Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                //startActivity(intent);
=======
        //deleteDatabase("/data/data/com.diev.aplicacion.diev/databases/usuario.db");
        //deleteDatabase("/data/data/com.diev.aplicacion.diev/databases/evento.db");
        // Tenemos una plantilla llamada splash.xml donde mostraremos la información que queramos (logotipo, etc.)
        setContentView(R.layout.splash);

        objBrl = new UsuarioBrl(SplashActivity.this);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación
                //Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                //startActivity(intent);
                cargarUser();
                leer();
>>>>>>> Luis
                finish();
            }
        }, DURACION_SPLASH);
    }

<<<<<<< HEAD

    public void leer(){
        /*String result ="";
        String columnas[]= {"usuarioId","nombre","sexo","edad","email" };
        Cursor c = this.getReadableDatabase().query("tblUsuario",columnas,null,null,null,null,null);

        int id, in,is,ie,iem;

        id = c.getColumnIndex("usuarioId");
        in = c.getColumnIndex("nombre");
        is = c.getColumnIndex("sexo");
        ie = c.getColumnIndex("edad");
        iem = c.getColumnIndex("email");

        c.moveToFirst(); //El primer registro de la tabla

        //     result  = c.getString(id)
        */

        try {
            UsuarioBrl objBrl = new UsuarioBrl(SplashActivity.this);;
            ArrayList<Usuario> nombre = objBrl.selectAll();
            if(nombre.size()== 0){
                Intent intent= new Intent(this, MainActivity.class);
                startActivity(intent);
            }else{
                Intent intent= new Intent(this, CalendarActivity.class);
                startActivity(intent);
            }

        }catch (Exception e){
            Intent intent= new Intent(this, CalendarActivity.class);
            startActivity(intent);
        }
    }
=======
    public void leer() {
        try {
            UsuarioBrl objBrl = new UsuarioBrl(SplashActivity.this);
            ArrayList<Usuario> usuarios = objBrl.selectAll();

            if (usuarios.size() == 0) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, CalendarActivity.class);
                startActivity(intent);
            }

        } catch (Exception e) {
            Log.e(TAG, e.toString());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Interpreta los resultados de la respuesta y así
     * realizar las operaciones correspondientes
     *
     * @param response Objeto Json con la respuesta
     */
    private void procesarRespuesta(JSONObject response) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString("estado");

            switch (estado) {
                case "1": // EXITO
                    // Obtener array "usuarios" Json
                    JSONArray mensaje = response.getJSONArray("user");
                    // Parsear con Gson
                    Usuario[] usuario = gson.fromJson(mensaje.toString(), Usuario[].class);
                    for (Usuario user : usuario) {
                        objBrl.insert(user);
                        Log.i(TAG, user.toString());
                    }
                    Intent intent = new Intent(this, CalendarActivity.class);
                    startActivity(intent);
                    finalize();
                    break;
                case "2": // FALLIDO
                    //leer();
                    break;
            }

        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * Carga el adaptador con las metas obtenidas
     * en la respuesta
     */
    public void cargarUser() {
        Log.d(TAG, "Cargo User");
        // Petición GET
        VolleySingleton.
                getInstance(this).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET,
                                null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Procesar la respuesta Json
                                        procesarRespuesta(response);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d(TAG, "Error Volley: " + error.toString());
                                    }
                                }

                        )
                );
    }

>>>>>>> Luis
}