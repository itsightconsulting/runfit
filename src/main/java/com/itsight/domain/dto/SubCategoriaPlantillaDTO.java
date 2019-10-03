package com.itsight.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubCategoriaPlantillaDTO implements Serializable {

    private Integer id;

    private String nombre;

    private Boolean favorito;

    public SubCategoriaPlantillaDTO() {
    }

    public SubCategoriaPlantillaDTO(Integer id, String nombre, Boolean favorito){
        this.id = id;
        this.nombre = nombre;
        this.favorito = favorito;
    }
}
