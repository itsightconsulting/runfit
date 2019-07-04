package com.itsight.domain.jsonb;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonDateSimpleDeserializer;
import com.itsight.json.JsonDateSimpleSerializer;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Data
public class CompetenciaRunner implements Serializable {

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    @Future
    private Date fecha;
    @Digits(integer = 2, fraction = 2)
    private Double distancia;
    @NotBlank
    @Size(min= 5, max = 60)
    private String nombre;
    private String tiempoObjetivo;
    private int prioridad;
}
