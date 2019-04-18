package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itsight.domain.jsonb.CuentaPago;
import com.itsight.domain.jsonb.Servicio;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainerFichaDTO implements Serializable {

    private int postulanteTrainerId;
    private String nombres;
    private String apellidos;
    private String correo;
    private String especialidad;
    private Integer disciplinaId;
    private Integer paisId;
    private String ubigeo;
    private String acerca;
    private String idiomas;
    private String estudios;
    private String metodoTrabajo;
    private String experiencias;
    private String resultados;
    private String niveles;
    private String centroTrabajo;
    private String especialidades;
    private String formasTrabajo;

    private String miniGaleria;

    private List<Servicio> servicios;
    private List<CuentaPago> cuentas;

    private String telefono;
    private String movil;
}
