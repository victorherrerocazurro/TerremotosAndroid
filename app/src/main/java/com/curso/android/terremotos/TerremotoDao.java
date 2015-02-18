package com.curso.android.terremotos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mañá on 11/02/2015.
 */
public class TerremotoDao {
    private SQLiteDatabase db;
    private DateFormat dateFormat;

    public TerremotoDao(SQLiteDatabase db) {
        this.db = db;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public long insertar(Terremoto terremoto){
        //Validar que el registro no existe en la BD
        Cursor cursor = consultar(terremoto.getTitulo());

        if (!cursor.moveToFirst()) {
            return db.insert(Terremoto.TABLA, Terremoto.CAMPO_TITULO, terremotoToContentValues(terremoto));
        } else {
            return -1;
        }
    }

    private Cursor consultar(String titulo){
        String[] proyeccion = {Terremoto.CAMPO_TITULO};
        String whereClause = Terremoto.CAMPO_TITULO + " = ?";
        String[] whereArgs = {titulo};
        return db.query(false, Terremoto.TABLA, proyeccion, whereClause, whereArgs, null, null, null, null);
    }

    public List<Terremoto> consultar (FiltroBusquedaDTO filtro){
        String[] proyeccion = {Terremoto.CAMPO_ID,
                Terremoto.CAMPO_TITULO, Terremoto.CAMPO_FECHA,
                Terremoto.CAMPO_LINK, Terremoto.CAMPO_MAGNITUD,
                Terremoto.CAMPO_LONGITUD, Terremoto.CAMPO_LATITUD};

        String whereClause = Terremoto.CAMPO_MAGNITUD + " > ? AND " + Terremoto.CAMPO_FECHA + " >= Datetime(?) ";
        String[] whereArgs = {filtro.getIntesidad().toString(), dateFormat.format(filtro.getFecha())};
        Cursor cursor = db.query(false, Terremoto.TABLA, proyeccion, whereClause, whereArgs, null, null, null, null);
        return cursorToTerremoto(cursor);
    }

    private ContentValues terremotoToContentValues(Terremoto terremoto) {

        ContentValues contentValues = new ContentValues();

        if (terremoto.getTitulo() != null) {
            contentValues.put(Terremoto.CAMPO_TITULO, terremoto.getTitulo());
        } else {
            contentValues.putNull(Terremoto.CAMPO_TITULO);
        }

        if(terremoto.getFecha() != null) {
            contentValues.put(Terremoto.CAMPO_FECHA, dateFormat.format(terremoto.getFecha()));
        } else {
            contentValues.putNull(Terremoto.CAMPO_FECHA);
        }
        if(terremoto.getLink() != null) {
            contentValues.put(Terremoto.CAMPO_LINK, terremoto.getLink());
        } else {
            contentValues.putNull(Terremoto.CAMPO_LINK);
        }
        if(terremoto.getMagnitud() != null) {
            contentValues.put(Terremoto.CAMPO_MAGNITUD, terremoto.getMagnitud());
        } else {
            contentValues.putNull(Terremoto.CAMPO_MAGNITUD);
        }
        if(terremoto.getLongitud() != null) {
            contentValues.put(Terremoto.CAMPO_LONGITUD, terremoto.getLongitud());
        } else {
            contentValues.putNull(Terremoto.CAMPO_LONGITUD);
        }
        if(terremoto.getLatitud() != null) {
            contentValues.put(Terremoto.CAMPO_LATITUD, terremoto.getLatitud());
        } else {
            contentValues.putNull(Terremoto.CAMPO_LATITUD);
        }

        return contentValues;
    }

    private List<Terremoto> cursorToTerremoto(Cursor cursor) {
        List<Terremoto> resultado = new LinkedList<>();

        if(cursor.moveToFirst()){
            do{
                Terremoto terremoto = new Terremoto();
                if(cursor.getColumnIndex(Terremoto.CAMPO_ID) != -1) {
                    terremoto.setId(cursor.getLong(cursor.getColumnIndex(Terremoto.CAMPO_ID)));
                }
                if(cursor.getColumnIndex(Terremoto.CAMPO_TITULO) != -1) {
                    terremoto.setTitulo(cursor.getString(cursor.getColumnIndex(Terremoto.CAMPO_TITULO)));
                }
                if(cursor.getColumnIndex(Terremoto.CAMPO_LINK) != -1) {
                    terremoto.setLink(cursor.getString(cursor.getColumnIndex(Terremoto.CAMPO_LINK)));
                }
                if(cursor.getColumnIndex(Terremoto.CAMPO_FECHA) != -1) {
                    try {
                        terremoto.setFecha(dateFormat.parse(cursor.getString(cursor.getColumnIndex(Terremoto.CAMPO_FECHA))));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(cursor.getColumnIndex(Terremoto.CAMPO_MAGNITUD) != -1) {
                    terremoto.setMagnitud(cursor.getFloat(cursor.getColumnIndex(Terremoto.CAMPO_MAGNITUD)));
                }
                if(cursor.getColumnIndex(Terremoto.CAMPO_LATITUD) != -1) {
                    terremoto.setLatitud(cursor.getFloat(cursor.getColumnIndex(Terremoto.CAMPO_LATITUD)));
                }
                if(cursor.getColumnIndex(Terremoto.CAMPO_LONGITUD) != -1) {
                    terremoto.setLongitud(cursor.getFloat(cursor.getColumnIndex(Terremoto.CAMPO_LONGITUD)));
                }
                resultado.add(terremoto);
            }while (cursor.moveToNext());
        }

        return resultado;
    }
}
