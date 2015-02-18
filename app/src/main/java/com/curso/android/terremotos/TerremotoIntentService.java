package com.curso.android.terremotos;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 */
public class TerremotoIntentService extends IntentService {

    public static final int NOTIFICACION_TERREMOTOS_ID = 2;

    public TerremotoIntentService() {
        super("TerremotontentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String urlPath = preferences.getString(
                getResources().getString(R.string.preferences_key_feedUrl),
                getResources().getString(R.string.preferences_key_feedUrl_default));

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification.Builder builderProgress = new Notification.Builder(this);

        builderProgress
                .setTicker("Progreso")
                .setSmallIcon(android.R.drawable.ic_menu_rotate)
                .setContentTitle("Cargando......")
                .setAutoCancel(true)
                .setProgress(0, 0, true);

        Notification notification = builderProgress.build();

        notificationManager.notify(NOTIFICACION_TERREMOTOS_ID, notification);

        try {
            //Se obtiene el Stream del XML
            InputStream is = descargarFeedRSS(urlPath);
            //Se parsea con PullParse el XML par obtener un List de Terremotos
            List<Terremoto> terremotos = TerremotosPullParser.parse(is);

            //Se obtiene la referencia de la Base de Datos
            TerremotoSQLiteOpenHelper terremotoSQLiteOpenHelper = new TerremotoSQLiteOpenHelper(
                    this,
                    getResources().getString(R.string.DataBaseName),
                    null,
                    getResources().getInteger(R.integer.DataBaseVersion));

            //Se crea el objeto DAO que gestiona la conexion de la base de datos
            SQLiteDatabase db = terremotoSQLiteOpenHelper.getWritableDatabase();
            TerremotoDao dao = new TerremotoDao(db);


            //Se insertan los terremotos en una transaccion
            try {
                db.beginTransaction();

                for (Terremoto terremoto : terremotos){
                    dao.insertar(terremoto);
                }

                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }

            builderProgress
                    .setProgress(1, 1, false)
                    .setContentTitle("Finalizado");
            notification = builderProgress.build();

            notificationManager.notify(NOTIFICACION_TERREMOTOS_ID, notification);

        } catch (Exception e) {
            e.printStackTrace();
            Notification.Builder builderError = new Notification.Builder(this);

            builderError
                    .setTicker("Error")
                    .setSmallIcon(android.R.drawable.ic_dialog_alert)
                    .setAutoCancel(true)
                    .setContentTitle(e.getMessage());

            notification = builderProgress.build();

            notificationManager.notify(NOTIFICACION_TERREMOTOS_ID, notification);
        }
    }

    private InputStream descargarFeedRSS(String urlPath) throws IOException {

        URL url = new URL(urlPath);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        InputStream is = con.getInputStream();

        return is;
    }
}
