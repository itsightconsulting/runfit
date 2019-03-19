package com.itsight.domain.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
public class PostDTO implements Serializable {

    private int id;

    private int tipo;

    private String titulo;

    private String descripcion;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String peso;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String duracion;

    public PostDTO(){}
}
