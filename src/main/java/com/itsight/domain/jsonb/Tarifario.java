package com.itsight.domain.jsonb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tarifario implements Serializable {

    private Integer personas;
    private Integer meses;
    private Integer sesiones;
    private String frecuencia;
    private Double precio;

}
