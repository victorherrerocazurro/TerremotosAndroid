package com.curso.android.terremotos;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MapActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //Funcionalidad para recoger de la intencion el terremoto
        Terremoto terremoto = (Terremoto) getIntent().getSerializableExtra(DetailActivity.EXTRA_TERREMOTO);

        if(terremoto == null){
            finish();
        }

        TerremotoMapFragment mapFragment = (TerremotoMapFragment) getFragmentManager().findFragmentById(R.id.map);

        if(mapFragment != null){
            mapFragment.dibujarTerremoto(terremoto);
        }
    }
}
