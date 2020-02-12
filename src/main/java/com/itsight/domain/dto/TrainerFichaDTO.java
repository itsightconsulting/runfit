package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itsight.domain.jsonb.CuentaPago;
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
    @NotNull
    @Size(min = 3, max = 1620)
    private String disciplinas;
    @Size(min=5, max = 80)
    private String especialidad;
    @Size(min = 80, max = 2000)
    @NotNull
    private String acerca;
    @Size(min = 2, max = 30)
    @NotNull
    private String idiomas;
    @Size(min = 8, max = 1820)
    private String estudios;
    @Size(min = 5, max = 1000)
    private String metodoTrabajo;
    @Size(min = 20, max = 1820)
    private String experiencias;
    @Size(min = 20, max = 1820)
    @NotNull
    private String resultados;
    @Size(min = 2, max = 20)
    @NotNull
    private String niveles;
    @Size(min = 10, max = 500)
    private String centroTrabajo;
    @Size(min = 10, max = 1620)
    private String especialidades;
    @Size(min = 2, max = 20)
    @NotNull
    private String formasTrabajo;
    @Size(min = 8, max = 150)
    private String horario;
    @Size(min= 10, max = 400)
    private String nota;
    @Valid
    private List<ServicioDTO> servicios;
    @Valid
    private List<CuentaPago> cuentas;
    @Size(max = 20)
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
    @Size(max = 480)
    private String redes;
    @Positive
    @Max(3)
    private Integer tipoTrainerId;

}
