package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElementoDel implements Serializable {


    private int numeroSemana;
    private int diaIndice;
    private int elementoIndice;
    private int subElementoIndice;
    private int minutos;
    private double distancia;
    private double calorias;

}
