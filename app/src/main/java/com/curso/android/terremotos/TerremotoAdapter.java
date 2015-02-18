package com.curso.android.terremotos;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mañá on 05/02/2015.
 */
public class TerremotoAdapter extends BaseAdapter {

    private Activity context;
    private int layout;
    private List<Terremoto> data;

    public TerremotoAdapter(Activity context, int layout, List<Terremoto> data) {
        this.context = context;
        this.layout = layout;
        this.data = data;
    }

    public void setData(List<Terremoto> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = context.getLayoutInflater().inflate(layout, null);

            TerremotoViewHelper helper = new TerremotoViewHelper();

            helper.titulo = (TextView) convertView.findViewById(R.id.titulo);
            helper.fecha = (TextView)convertView.findViewById(R.id.fecha);
            helper.icono = (ImageView)convertView.findViewById(R.id.icono);

            convertView.setTag(helper);
        }

        Terremoto terremoto = data.get(position);

        TerremotoViewHelper helper = (TerremotoViewHelper) convertView.getTag();

        helper.titulo.setText(terremoto.getTitulo());
        helper.fecha.setText(terremoto.getFecha().toString());
        helper.icono.setImageResource(establecerIcono(terremoto.getMagnitud()));

        return convertView;
    }

    private class TerremotoViewHelper{
        ImageView icono;
        TextView titulo;
        TextView fecha;
    }

    private int establecerIcono(float magnitud){
        if (magnitud <= 2.5){
            return R.drawable.terremoto_verde;
        } else if (2.5 < magnitud && magnitud <= 4.5){
            return R.drawable.terremoto_naranja;
        } else if (4.5 < magnitud){
            return R.drawable.terremoto_rojo;
        } else{
            return R.drawable.terremoto_desconocido;
        }
    }
}
