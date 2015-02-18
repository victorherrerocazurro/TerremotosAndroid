package com.curso.android.terremotos;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by mañá on 05/02/2015.
 */
public class Terremoto implements Serializable{

    public static final String TABLA = "TERREMOTOS";
    public static final String CAMPO_TITULO = "TITULO";
    public static final String CAMPO_ID = "ID";
    public static final String CAMPO_FECHA = "FECHA";
    public static final String CAMPO_MAGNITUD = "MAGNITUD";
    public static final String CAMPO_LATITUD = "LATITUD";
    public static final String CAMPO_LONGITUD = "LONGITUD";
    public static final String CAMPO_LINK = "LINK";

    private Long id;
    private String titulo;
    private Float magnitud;
    private String link;
    private Date fecha;
    private Float latitud;
    private Float longitud;

    public Terremoto() {
    }

    public Terremoto(String titulo, Float magnitud, String link, Date fecha, Float latitud, Float longitud) {
        this.titulo = titulo;
        this.magnitud = magnitud;
        this.link = link;
        this.fecha = fecha;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Float getMagnitud() {
        return magnitud;
    }

    public void setMagnitud(Float magnitud) {
        this.magnitud = magnitud;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }
}
