package com.itsight.domain.pojo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.Rutina;
import com.itsight.domain.TipoRutina;
import com.itsight.domain.jsonb.RutinaControl;
import com.itsight.json.JsonDateSimpleDeserializer;
import com.itsight.json.JsonDateSimpleSerializer;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
public class RutinaPOJO implements Serializable {

    private int anios;
    private int meses;
    private int totalSemanas;
    private int dias;
    private Integer tipoRutina;
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fechaCliAcceso;
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    private RutinaControl control;
    public String nombre;
    public String prueba;

    public RutinaPOJO(){}

    public RutinaPOJO(Rutina rutina) {
        this.anios = rutina.getAnios();
        this.meses = rutina.getMeses();
        this.totalSemanas = rutina.getTotalSemanas();
        this.dias = rutina.getDias();
        this.tipoRutina = rutina.getTipoRutina().getId();
        this.fechaCliAcceso = rutina.getFechaCliAcceso();
        this.fechaInicio = rutina.getFechaInicio();
        this.fechaFin = rutina.getFechaFin();
        this.control = rutina.getControl();
        this.nombre = rutina.getNombre();
    }
}
