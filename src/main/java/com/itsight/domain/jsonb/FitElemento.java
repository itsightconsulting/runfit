package com.itsight.domain.jsonb;


import java.io.Serializable;

public class FitElemento implements Serializable {

    private int id;
    private String nombre;

    public FitElemento(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public FitElemento(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

}
