package com.itsight.domain.jsonb;

import lombok.Data;

import java.io.Serializable;

@Data
public class CondicionMejora implements Serializable {

    private int id;
    private String nombre;

    public CondicionMejora(){}

    public CondicionMejora(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
