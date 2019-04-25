package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itsight.domain.jsonb.CuentaPago;
import com.itsight.domain.jsonb.Servicio;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainerFichaDTO implements Serializable {

    private int postulanteTrainerId;
    @Size(min=5, max = 50)
    @NotNull
    private String apellidos;
    @Size(min=5, max = 50)
    @NotNull
    private String nombres;
    @Size(max = 65)
    @NotNull
    private String correo;
    @Size(max = 200)
    @NotNull
    private String especialidad;
    @Min(value = 1)
    @NotNull
    private Integer disciplinaId;
    @Min(value = 1)
    @NotNull
    private Integer paisId;
    @Size(min = 3)
    @NotNull
    private String ubigeo;
    @Size(min = 80, max = 2000)
    @NotNull
    private String acerca;
    @Size(min = 4, max = 100)
    @NotNull
    private String idiomas;
    @Size(min = 8, max = 500)
    @NotNull
    private String estudios;
    @Size(min = 5, max = 1000)
    @NotNull
    private String metodoTrabajo;
    @Size(min = 20, max = 500)
    @NotNull
    private String experiencias;
    @Size(min = 10, max = 500)
    @NotNull
    private String resultados;
    @Size(min = 5, max = 80)
    @NotNull
    private String niveles;
    @Size(min = 10, max = 120)
    @NotNull
    private String centroTrabajo;
    @Size(min = 10, max = 100)
    @NotNull
    private String especialidades;
    @Size(min = 10, max = 100)
    @NotNull
    private String formasTrabajo;
    @Size(min = 6, max = 40)
    @NotNull
    private String nomPag;
    private String miniGaleria;
    @NotNull
    @Valid
    private List<Servicio> servicios;
    @NotNull
    @Valid
    private List<CuentaPago> cuentas;
    private String telefono;
    private String movil;

}
