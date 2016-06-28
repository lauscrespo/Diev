package com.diev.aplicacion.diev;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;

import com.diev.aplicacion.diev.brl.UsuarioBrl;
import com.diev.aplicacion.diev.model.Usuario;

import java.util.ArrayList;


public class SplashActivity extends Activity {

    // Duración en milisegundos que se mostrará el splash
    private final int DURACION_SPLASH = 3000; // 3 segundos

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Tenemos una plantilla llamada splash.xml donde mostraremos la información que queramos (logotipo, etc.)
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable(){
            public void run(){
                // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación

                leer();
                //Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                //startActivity(intent);
                finish();
            }
        }, DURACION_SPLASH);
    }


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
}