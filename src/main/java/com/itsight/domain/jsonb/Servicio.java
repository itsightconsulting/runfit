package com.itsight.domain.jsonb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Servicio implements Serializable {

    private String nombre;

    private String descripcion;

    private String incluye;

    private String infoAdicional;

    private List<Tarifario> tarifarios;

    private String condicionesServicioRuWeb;

    private String condicionesServicioRuReal;

}
