package com.itsight.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.jsonb.RutinaControl;
import com.itsight.json.JsonDateSimpleSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class RuTpGeneralDTO implements Serializable {

    private Integer redFitnessId;
    private Integer clienteId;
    private int anios;
    private int meses;
    private int totalSemanas;
    private int dias;
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fechaInicio;
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fechaFin;
    private List<SemanaPlantillaDTO> semanas;
    private RutinaControl control;
}
