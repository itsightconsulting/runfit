package com.itsight.domain.jsonb;

import lombok.Data;

import java.io.Serializable;

@Data
public class CompetenciaRunner implements Serializable {

    private String fecha;
    private int distancia;
    private String nombre;
    private String tiempoObjetivo;
    private int prioridad;
}
