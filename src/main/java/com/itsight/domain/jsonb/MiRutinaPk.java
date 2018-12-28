package com.itsight.domain.jsonb;

import lombok.Data;

import java.io.Serializable;

@Data
public class MiRutinaPk implements Serializable {

    private int id;
    private String nombre;
    private String hash;
    private int nvl;

    public MiRutinaPk(){}

    public MiRutinaPk(int id){
        this.id = id;
    }

    public MiRutinaPk(int id, String nombre, String hash, int nvl) {
        this.id = id;
        this.nombre = nombre;
        this.hash = hash;
        this.nvl = nvl;
    }
}
