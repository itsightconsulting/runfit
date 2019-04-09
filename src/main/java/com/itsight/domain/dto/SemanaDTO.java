package com.itsight.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SemanaDTO implements Serializable {

    private Integer id;

    private Date fechaInicio;

    private Date fechaFin;

    private boolean flagFull;

    private List<DiaDTO> lstDia;

    private double kilometrajeTotal;

    private double kilometrajeActual;

    private double calorias;

    private double horas;

    private String objetivos;

    private String metricas;

    private String metricasVelocidad;

    public SemanaDTO(){}
}
