package com.itsight.domain.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonDateSimpleSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RuCliPOJO implements Serializable {

    private int id;
    private int anios;
    private int meses;
    private int totalSemanas;
    private int dias;
    private Integer tipoRutina;
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fechaInicio;
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fechaFin;

    private String control;
    private Integer rows;


    private String avanceSemanal;
    private String esfuerzoSemanal;

    public RuCliPOJO(){}

    public RuCliPOJO(int id, int anios, int meses, int totalSemanas, int dias, Integer tipoRutina, Date fechaInicio, Date fechaFin, String control, Integer rows) {
        this.id = id;
        this.anios = anios;
        this.meses = meses;
        this.totalSemanas = totalSemanas;
        this.dias = dias;
        this.tipoRutina = tipoRutina;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.control = control;
        this.rows = rows;
    }

    public RuCliPOJO(String avanceSemanal , String esfuerzoSemanal){
        this.avanceSemanal = avanceSemanal;
        this.esfuerzoSemanal = esfuerzoSemanal;
    }
}
