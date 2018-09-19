package com.itsight.domain.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
public class RutinaDto implements Serializable {
    private int redFitnessId;
    private int clienteId;
    private int anios;
    private int meses;
    private int totalSemanas;
    private int dias;
    private int tipoRutina;
    private int contadorRutinas;
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fechaInicio;
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fechaFin;

    private List<SemanaPlantillaDto> semanas;

    public RutinaDto(){}

}
