package com.diev.aplicacion.diev;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.diev.aplicacion.diev.brl.UsuarioBrl;
import com.diev.aplicacion.diev.imagenes.AdaptadorDeRopa;
import com.diev.aplicacion.diev.imagenes.Ropa;
import com.diev.aplicacion.diev.model.Usuario;

import java.util.ArrayList;


public class ViewSuggestion extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private GridView gridView;
    private AdaptadorDeRopa adaptador;


    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_suggestion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gridView = (GridView) findViewById(R.id.grid);
        adaptador = new AdaptadorDeRopa(this);
        gridView.setAdapter(adaptador);
        gridView.setOnItemClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Ropa item = (Ropa) parent.getItemAtPosition(position);

        Intent intent = new Intent(this, SuggestionDetail.class);
        intent.putExtra(SuggestionDetail.EXTRA_PARAM_ID, item.getId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ActivityOptionsCompat activityOptions =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                            this,
                            new Pair<View, String>(view.findViewById(R.id.imagen_ropa),
                                    SuggestionDetail.VIEW_NAME_HEADER_IMAGE)
                    );

            ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
        } else
            startActivity(intent);
    }
}
