package com.itsight.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class TrainerFichaPOJO implements Serializable {

    private Integer id;

    private String nombres;

    private String apellidos;

    private String especialidad;

    private String ubigeo;

    private String acerca;

    private int canPerValoracion;

    private Double totalValoracion;

    private String rutaWebImg;

    private String servicios;

    private String nomPag;

    public TrainerFichaPOJO() {
    }
}
