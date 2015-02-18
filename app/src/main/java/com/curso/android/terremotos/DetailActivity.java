package com.curso.android.terremotos;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.Serializable;


public class DetailActivity extends ActionBarActivity {

    public static final String EXTRA_TERREMOTO = "terremoto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Terremoto terremoto = (Terremoto) getIntent().getSerializableExtra(EXTRA_TERREMOTO);

        EditText id = (EditText) findViewById(R.id.etIdTerremoto);
        EditText titulo = (EditText) findViewById(R.id.etTituloTerremoto);
        EditText magnitud = (EditText) findViewById(R.id.etMagnitudTerremoto);
        EditText fecha = (EditText) findViewById(R.id.etFechaTerremoto);
        EditText link = (EditText) findViewById(R.id.etLinkTerremoto);
        EditText latitud = (EditText) findViewById(R.id.etLatitudTerremoto);
        EditText longitud = (EditText) findViewById(R.id.etLongitudTerremoto);

        id.setText(String.valueOf(terremoto.getId()));
        titulo.setText(terremoto.getTitulo());
        magnitud.setText(String.valueOf(terremoto.getMagnitud()));
        fecha.setText(String.valueOf(terremoto.getFecha()));
        link.setText(terremoto.getLink());
        latitud.setText(String.valueOf(terremoto.getLatitud()));
        longitud.setText(String.valueOf(terremoto.getLongitud()));

        View btVerEnMapa = findViewById(R.id.btVerEnMapa);

        btVerEnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MapActivity.class);
                intent.putExtra(EXTRA_TERREMOTO, terremoto);
                startActivity(intent);
            }
        });
    }
}
