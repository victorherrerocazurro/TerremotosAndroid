package com.curso.android.terremotos;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by mañá on 05/02/2015.
 */
public class FiltroBusquedaDTO implements Serializable{
    private Integer intesidad;
    private Date fecha;

    public FiltroBusquedaDTO(Integer intesidad, Date fecha) {
        this.intesidad = intesidad;
        this.fecha = fecha;
    }

    public Integer getIntesidad() {
        return intesidad;
    }

    public Date getFecha() {
        return fecha;
    }
}
