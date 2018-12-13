package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ElementoUpd implements Serializable {

    private String nombre;
    private int diaIndice;
    private int elementoIndice;
    private int subElementoIndice;
    private int numeroSemana;
    private int minutos;
    private double distancia;
    private double calorias;

}
