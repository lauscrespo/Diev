package com.diev.aplicacion.diev;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.diev.aplicacion.diev.brl.UsuarioBrl;
import com.diev.aplicacion.diev.model.Usuario;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    UsuarioBrl objBrl;
    private int usuarioId;
    private EditText txtNombre;
    private String Edad;
    private EditText txtEmail;
    private RadioButton rbMujer;
    private RadioButton rbHombre;


    public static final String PARAM_USUARIO_ID = "PARAM_USUARIO_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        objBrl = new UsuarioBrl(MainActivity.this);
        usuarioId = getIntent().getIntExtra(PARAM_USUARIO_ID, 0);
        txtNombre = (EditText) findViewById(R.id.txt_Nombre);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        rbMujer = (RadioButton) findViewById(R.id.radioButtonMujer);
        rbHombre = (RadioButton) findViewById(R.id.radioButtonHombre);

        Spinner sp = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this, R.array.edades, R.layout.spinner_item);
        sp.setAdapter(adapter);
        Button btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUsuario();
            }
        });

        Button btnShare = (Button) findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                shareSocialNetwork();
            }
        });


        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                         public void onItemSelected(AdapterView<?> parentView, View SelectItemView, int position, long id) {
                                             Edad = parentView.getItemAtPosition(position).toString();
                                         }

                                         @Override
                                         public void onNothingSelected(AdapterView<?> parent) {

                                         }
                                     }

        );

    }



    public void shareSocialNetwork() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_SUBJECT, "Diev");
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.ea.game.tetris2011_row&hl=es-419");
         startActivity(Intent.createChooser(share, "Compartir usando..."));
    }


    ///evento que guarda el usuario
    private void saveUsuario() {
        String nombre = txtNombre.getText().toString();
        String edad = Edad;
        String email = txtEmail.getText().toString();

        if (nombre.trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el nombre", Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el email", Toast.LENGTH_SHORT).show();
            return;
        }
        String result = nombre.replace(" ", "");
        Usuario usuario = new Usuario();
        //llenar datos usuario
        usuario.setUsuarioId(0);
        usuario.setNombre(nombre);
        usuario.setEdad(edad);
        usuario.setEmail(email);
        if (rbMujer.isChecked()) {
            usuario.setSexo("Mujer");
        }
        if (rbHombre.isChecked()) {
            usuario.setSexo("Hombre");
        }

        try {
            objBrl.insert(usuario);
            Toast.makeText(this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("MainActivity", "error al insertar usuario");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
