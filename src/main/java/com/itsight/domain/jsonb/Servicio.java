package com.itsight.domain.jsonb;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Servicio implements Serializable {

    private String nombre;

    private String descripcion;

    private String condicionesServicioRuWeb;

    private String condicionesServicioRuReal;

    private List<String> incluidos;
}
