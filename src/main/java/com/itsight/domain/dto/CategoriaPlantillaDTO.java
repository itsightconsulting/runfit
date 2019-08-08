package com.itsight.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoriaPlantillaDTO implements Serializable {

    private Integer id;

    private String nombre;

    private int tipo;

    public CategoriaPlantillaDTO() {
    }

    public CategoriaPlantillaDTO(Integer id, String nombre, int tipo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
    }
}
