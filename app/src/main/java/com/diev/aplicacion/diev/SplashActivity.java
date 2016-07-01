package com.diev.aplicacion.diev;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.diev.aplicacion.diev.brl.UsuarioBrl;
import com.diev.aplicacion.diev.model.Usuario;

import java.util.ArrayList;


public class SplashActivity extends Activity {

    // Duración en milisegundos que se mostrará el splash
    private final int DURACION_SPLASH = 3000; // 3 segundos
    private static SplashActivity myContext;

    public SplashActivity() {
        myContext = this;
    }

    public static SplashActivity getInstance() {
        return myContext;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //deleteDatabase("/data/data/com.diev.aplicacion.diev/databases/usuario.db");
        //deleteDatabase("/data/data/com.diev.aplicacion.diev/databases/evento.db");
        // Tenemos una plantilla llamada splash.xml donde mostraremos la información que queramos (logotipo, etc.)
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación
                //Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                //startActivity(intent);
                leer();
                finish();
            }
        }, DURACION_SPLASH);
    }

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
            Log.e("SpalshActivity", e.toString());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}