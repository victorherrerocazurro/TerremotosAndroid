package com.curso.android.terremotos;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends ActionBarActivity implements DatePickerDialog.OnDateSetListener {

    private Spinner intensidades;
    private EditText fecha;
    private Calendar c = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intensidades = (Spinner) findViewById(R.id.sp_intensidades);
        fecha = (EditText) findViewById(R.id.tx_fecha);

        updateLabel();


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
        switch (id) {
            case  R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.action_help:
                startActivity(new Intent(this, HelpActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buscar(View view){
        Intent intent = new Intent(this, ListActivity.class);

        FiltroBusquedaDTO filtro = new FiltroBusquedaDTO(
                Integer.parseInt(intensidades.getSelectedItem().toString()),
                c.getTime());

        intent.putExtra(ListActivity.FILTRO_KEY, filtro);

        startActivity(intent);
    }

    public void mostrarDialogo(View view){

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, year, month, day);

        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel();
    }

    private void updateLabel() {
        Date date = c.getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        fecha.setText(format.format(date));
    }
}
