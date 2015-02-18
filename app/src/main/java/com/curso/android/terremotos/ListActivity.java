package com.curso.android.terremotos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;


public class ListActivity extends ActionBarActivity {

    public static final String FILTRO_KEY = "filtro";

    private ListView lvTerremotos;
    private ProgressDialog dialogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        FiltroBusquedaDTO filtro = (FiltroBusquedaDTO) getIntent().getSerializableExtra(FILTRO_KEY);

        TerremotoAdapter terremotoAdapter = new TerremotoAdapter(this,R.layout.terremoto_list_item,new LinkedList<Terremoto>());

        lvTerremotos = (ListView) findViewById(R.id.lvTerremotos);
        lvTerremotos.setAdapter(terremotoAdapter);

        dialogo = new ProgressDialog(this);
        dialogo.setMessage("Buscando.....");

        BusquedaTerremotosAsyncTask task = new BusquedaTerremotosAsyncTask(this, lvTerremotos, dialogo);

        task.execute(filtro);

        registerForContextMenu(lvTerremotos);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        switch (v.getId()){
            case R.id.lvTerremotos:
                int position = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
                Terremoto item = (Terremoto) lvTerremotos.getAdapter().getItem(position);
                menu.setHeaderTitle(item.getTitulo());
                getMenuInflater().inflate(R.menu.menu_contexto_listado_terremotos,menu);
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_detalle:
                int position = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
                Serializable terremoto = (Serializable) lvTerremotos.getAdapter().getItem(position);

                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_TERREMOTO, terremoto);
                startActivity(intent);
                break;
        }


        return super.onContextItemSelected(item);
    }
}
