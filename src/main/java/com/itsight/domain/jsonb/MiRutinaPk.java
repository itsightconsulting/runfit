package com.itsight.domain.jsonb;

import lombok.Data;

import java.io.Serializable;

@Data
public class MiRutinaPk implements Serializable {

    private Long id;
    private String nombre;
    private String hash;
    private int nvl;

    public MiRutinaPk(){}

    public MiRutinaPk(Long id){
        this.id = id;
    }

    public MiRutinaPk(Long id, String nombre, String hash, int nvl) {
        this.id = id;
        this.nombre = nombre;
        this.hash = hash;
        this.nvl = nvl;
    }
}
