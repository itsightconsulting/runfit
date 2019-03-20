package com.itsight.domain.jsonb;

import lombok.Data;

import java.io.Serializable;

@Data
public class MiRutinaPk implements Serializable {

    private Integer id;
    private String nombre;
    private String hash;
    private int nvl;

    public MiRutinaPk(){}

    public MiRutinaPk(Integer id){
        this.id = id;
    }

    public MiRutinaPk(Integer id, String nombre, String hash, int nvl) {
        this.id = id;
        this.nombre = nombre;
        this.hash = hash;
        this.nvl = nvl;
    }
}
