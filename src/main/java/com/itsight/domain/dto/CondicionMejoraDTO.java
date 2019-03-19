package com.itsight.domain.dto;

import lombok.Data;

@Data
public class CondicionMejoraDTO {

    private int id;
    private String nombre;

    public CondicionMejoraDTO() { }

    public CondicionMejoraDTO(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
