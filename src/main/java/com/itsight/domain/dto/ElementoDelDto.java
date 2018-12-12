package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Max;
import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElementoDelDto implements Serializable {


    private String numeroSemana;
    private String diaIndice;
    private String elementoIndice;
    private String subElementoIndice;
    @Max(value = 50)
    private int minutos;
    private double distancia;
    @Max(value = 300)
    private double calorias;

}
