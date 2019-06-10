package com.itsight.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
public class AnuncioPOJO implements Serializable {

    private Integer id;
    private String nombre;
    private String titulo;
    private String mensaje;
    private String img;
    private Date fechaCreacion;
    private boolean flagLeido;

}
