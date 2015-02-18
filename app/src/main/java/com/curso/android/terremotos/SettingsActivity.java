package com.curso.android.terremotos;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class SettingsActivity extends Activity {

    private TerremotosSharedPreferencesChangeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //Creacion del objeto Listener
        listener = new TerremotosSharedPreferencesChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Registro de Listener para conocer que las preferencias han cambiado
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Desregistro del Listener
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(listener);
    }
}
