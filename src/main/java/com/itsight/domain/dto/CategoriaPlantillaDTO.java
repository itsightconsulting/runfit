package com.itsight.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoriaPlantillaDTO implements Serializable {

    private Integer id;

    private String nombre;

    private int tipo;

    private Boolean favorito;

    public CategoriaPlantillaDTO() {
    }

    public CategoriaPlantillaDTO(Integer id, String nombre, int tipo, Boolean favorito) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.favorito = favorito;
    }
}
