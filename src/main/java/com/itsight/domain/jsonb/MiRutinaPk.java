package com.itsight.domain.jsonb;

import lombok.Data;

import java.io.Serializable;

@Data
public class MiRutinaPk implements Serializable {

    private int id;
    private String nombre;

    public MiRutinaPk(){}

    public MiRutinaPk(int id){
        this.id = id;
    }
}
