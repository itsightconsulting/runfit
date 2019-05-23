package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itsight.domain.jsonb.CuentaPago;
import com.itsight.domain.jsonb.Servicio;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainerFichaDTO implements Serializable {

    @NotBlank
    @Size(max = 32)
    private String trainerId;
    @NotNull
    @Positive
    @Max(3)
    private Integer sexo;
    @Size(max = 200)
    @NotBlank
    private String especialidad;
    @Size(min = 80, max = 2000)
    @NotNull
    private String acerca;
    @Size(min = 2, max = 100)
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
    @Size(min = 2, max = 80)
    @NotNull
    private String niveles;
    @Size(min = 10, max = 120)
    @NotNull
    private String centroTrabajo;
    @Size(min = 10, max = 100)
    @NotNull
    private String especialidades;
    @Size(min = 2, max = 50)
    @NotNull
    private String formasTrabajo;
    @Size(min = 8, max = 150)
    private String horario;
    @Size(max = 400)
    private String nota;
    @Valid
    private List<Servicio> servicios;
    @Valid
    private List<CuentaPago> cuentas;
    @NotBlank
    private String mediosPago;
    @Size(max = 3000)
    private String miniGaleria;
    @Size(min = 3, max = 200)
    @NotNull
    private String imgExt;
    @Size(max = 100)
    private String mapCoordenadas;
    @Digits(integer = 5, fraction = 2)
    private Double mapCircleRadio;
    @Size(max = 600)
    private String redes;

}
