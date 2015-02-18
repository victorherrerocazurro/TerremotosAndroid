package com.curso.android.terremotos;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Victor on 16/02/2015.
 */
public class TerremotosSharedPreferencesChangeListener implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int START_SERVICE_REQUEST_CODE = 1;
    private Context context;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    public TerremotosSharedPreferencesChangeListener(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        pendingIntent = PendingIntent.getService(
                context,
                START_SERVICE_REQUEST_CODE,
                new Intent(context, TerremotoIntentService.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Si cambian las preferencia de frecuencia, hay que comunicar con el servicio que
        // gestiona la descarga del XML
        if(key.equals(context.getResources().getString(R.string.preferences_key_actualizacion))){

            String actualizacion = sharedPreferences.getString(
                    context.getResources().getString(R.string.preferences_key_actualizacion),
                    context.getResources().getString(R.string.preferences_key_actualizacion_default));

            Long actualizacionLong = Long.parseLong(actualizacion);

            //Se cancela la anterior tarea programada
            alarmManager.cancel(pendingIntent);

            //Si es distinto de 0 e, periodo de actualización, se pone en marcha de nuevo la
            //tarea programada con el nuevo valor
            if (actualizacionLong != 0) {
                alarmManager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP,
                        0,
                        actualizacionLong * 1000,
                        pendingIntent);
            }
        }
    }
}
