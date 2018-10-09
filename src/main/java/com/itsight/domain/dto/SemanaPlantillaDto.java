package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonDateSimpleSerializer;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SemanaPlantillaDto {

    private int id;
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fechaInicio;
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fechaFin;
    private boolean flagFull;
    private int totalSemanas;
    private double kilometrajeTotal;
    private String metricas;
    private String metricasVelocidad;

    private List<DiaPlantillaDto> dias;

    public SemanaPlantillaDto(){}

}
