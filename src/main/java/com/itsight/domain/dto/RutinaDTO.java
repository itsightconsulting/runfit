package com.itsight.domain.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.jsonb.*;
import com.itsight.json.JsonDateSimpleSerializer;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RutinaDTO implements Serializable {
    private Integer redFitnessId;
    private Integer clienteId;
    private int anios;
    private int meses;
    private int totalSemanas;
    private int dias;
    private int tipoRutina;
    private int contadorRutinas;
    private String matrizMejoraVelocidades;
    private String matrizMejoraCadencia;
    private String matrizMejoraTcs;
    private String matrizMejoraLonPaso;
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fechaInicio;
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fechaFin;

    private RuGeneral general;

    private RuStats stats;

    private RuMejoras mejoras;

    private List<RuDataGrafico> dtGrafico;

    private List<SemanaPlantillaDTO> semanas;

    private RutinaControl control;

    public RutinaDTO(){}

}
