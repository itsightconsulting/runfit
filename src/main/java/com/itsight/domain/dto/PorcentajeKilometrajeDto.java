package com.itsight.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PorcentajeKilometrajeDto implements Serializable {

    private int indice;
    private int distancia;
    private int etapa;
    private String porcentajes;

}
