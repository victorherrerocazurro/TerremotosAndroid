package com.curso.android.terremotos;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mañá on 09/02/2015.
 */
public class BusquedaTerremotosAsyncTask extends AsyncTask<FiltroBusquedaDTO, Void, List<Terremoto>> {

    private final ListView listViewTerremotos;
    private ProgressDialog dialogo;
    private Context context;

    public BusquedaTerremotosAsyncTask(Context context, ListView listViewTerremotos, ProgressDialog dialogo) {
        this. context = context;
        this.listViewTerremotos = listViewTerremotos;
        this.dialogo = dialogo;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialogo.show();
        dialogo.setProgress(0);
    }

    @Override
    protected List<Terremoto> doInBackground(FiltroBusquedaDTO... params) {

        TerremotoSQLiteOpenHelper db = new TerremotoSQLiteOpenHelper(
                context,
                context.getResources().getString(R.string.DataBaseName),
                null,
                context.getResources().getInteger(R.integer.DataBaseVersion));

        TerremotoDao dao = new TerremotoDao(db.getWritableDatabase());

        return dao.consultar(params[0]);
    }

    @Override
    protected void onPostExecute(List<Terremoto> terremotos) {
        super.onPostExecute(terremotos);
        TerremotoAdapter adapter = (TerremotoAdapter) listViewTerremotos.getAdapter();
        adapter.setData(terremotos);
        adapter.notifyDataSetChanged();
        dialogo.hide();
    }
}
