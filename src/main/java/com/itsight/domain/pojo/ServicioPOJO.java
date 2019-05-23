package com.itsight.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ServicioPOJO implements Serializable {

    private Integer id;
    private String nombre;
    private String descripcion;
    private String incluye;
    private String infoAdicional;
    private String tarifarios;
    private String tycFile;
}
