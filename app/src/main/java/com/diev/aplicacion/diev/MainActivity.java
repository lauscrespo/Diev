package com.diev.aplicacion.diev;

import android.app.Activity;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.diev.aplicacion.diev.brl.UsuarioBrl;
import com.diev.aplicacion.diev.model.Usuario;
import com.diev.aplicacion.diev.tools.Constantes;
import com.diev.aplicacion.diev.web.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    /*
   Etiqueta de depuracion
    */
    private static final String TAG = SplashActivity.class.getSimpleName();

    private UsuarioBrl objBrl;
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
                guardarUser();
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

    /**
     * Guarda los datos de un usuario.
     */
    public void guardarUser() {

        // Obtener valores actuales de los controles
        final String nombre = txtNombre.getText().toString();
        final String edad = Edad;
        String sexo = "";
        final String email = txtEmail.getText().toString();
        if (rbMujer.isChecked()) {
            sexo = "Mujer";
        }
        if (rbHombre.isChecked()) {
            sexo = "Hombre";
        }

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("nombre", nombre);
        map.put("sexo", sexo);
        map.put("edad", edad);
        map.put("email", email);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        // Depurando objeto Json...
        Log.d(TAG, jobject.toString());

        // Actualizar datos en el servidor
        VolleySingleton.getInstance(this).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Constantes.INSERT,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar la respuesta del servidor
                                procesarRespuesta(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Volley: " + error.getMessage());
                            }
                        }

                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("Accept", "application/json");
                        return headers;
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8" + getParamsEncoding();
                    }
                }
        );

    }

    /**
     * Procesa la respuesta obtenida desde el sevidor
     *
     * @param response Objeto Json
     */
    private void procesarRespuesta(JSONObject response) {

        try {
            // Obtener estado
            String estado = response.getString("estado");
            // Obtener mensaje
            String mensaje = response.getString("mensaje");

            switch (estado) {
                case "1":
                    // Enviar código de éxito
                    this.setResult(Activity.RESULT_OK);
                    // Terminar actividad
                    this.finish();
                    break;

                case "2":
                    // Enviar código de falla
                    this.setResult(Activity.RESULT_CANCELED);
                    // Terminar actividad
                    this.finish();
                    break;
            }

            Intent intent = new Intent(this, CalendarActivity.class);
            startActivity(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
