package com.diev.aplicacion.diev;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.diev.aplicacion.diev.brl.UsuarioBrl;
import com.diev.aplicacion.diev.imagenes.AdaptadorDeRopa;
import com.diev.aplicacion.diev.imagenes.Ropa;
import com.diev.aplicacion.diev.model.Usuario;

import java.util.ArrayList;


public class SuggestionDetail extends AppCompatActivity {

    public static final String EXTRA_PARAM_ID = "com.herprogramacion.coches2015.extra.ID";
    public static final String VIEW_NAME_HEADER_IMAGE = "imagen_compartida";
    private Ropa itemDetallado;
    private ImageView imagenExtendida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_suggestion);

        // Obtener el coche con el identificador establecido en la actividad principal
        itemDetallado = AdaptadorDeRopa.getItemRopa(getIntent().getIntExtra(EXTRA_PARAM_ID, 0));

        imagenExtendida = (ImageView) findViewById(R.id.imagen_extendida);

        try {
            cargarImagenExtendida();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarImagenExtendida() throws Exception {
        Glide.with(imagenExtendida.getContext())
                .load(itemDetallado.getIdDrawable())
                .into(imagenExtendida);

    }
}