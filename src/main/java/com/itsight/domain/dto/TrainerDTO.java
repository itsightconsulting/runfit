package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itsight.domain.jsonb.CuentaPago;
import com.itsight.validation.ExtendedEmailValidator;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainerDTO implements Serializable {

    private int postulanteTrainerId;
    @Size(min=3, max = 50)
    @NotNull
    private String apellidos;
    @Size(min=3, max = 50)
    @NotNull
    private String nombres;
    @Size(min = 7, max = 40)
    @NotNull
    @ExtendedEmailValidator
    private String correo;
    @Size(min=5, max = 40)
    @NotNull
    private String username;
    @Size(min=8, max = 30)
    @NotBlank
    private String password;
    @NotNull
    @Min(1)
    @Max(3)
    private Integer sexo;
    @Size(max = 200)
    private String especialidad;
    @Positive
    @NotNull
    private Integer tipoDocumentoId;
    @Size(max = 16)
    @NotBlank
    private String documento;
    @Positive
    @NotNull
    private Integer paisId;
    @Size(max = 10)
    @NotBlank
    private String ubigeo;
    @Size(min = 80, max = 2000)
    @NotNull
    private String acerca;
    @Size(min = 2, max = 100)
    @NotNull
    private String idiomas;
    @Size(min = 8, max = 500)
    private String estudios;
    @Size(min = 5, max = 1000)
    @NotNull
    private String metodoTrabajo;
    @Size(min = 20, max = 500)
    private String experiencias;
    @Size(min = 10, max = 500)
    @NotNull
    private String resultados;
    @Size(min = 2, max = 80)
    @NotNull
    private String niveles;
    @Size(min = 10, max = 120)
    private String centroTrabajo;
    @Size(min = 10, max = 100)
    private String especialidades;
    @Size(min = 2, max = 50)
    @NotNull
    private String formasTrabajo;
    @Size(min = 8, max = 150)
    private String horario;
    @Size(min = 6, max = 40)
    @NotNull
    private String nomPag;
    @Size(max = 10)
    private String cantidadFiles;
    @Size(max = 50)
    private String ixsCondSvcFile;
    @Size(max = 400)
    private String nota;
    @Valid
    private List<ServicioDTO> servicios;
    @Valid
    private List<CuentaPago> cuentas;
    @Size(max = 20)
    private String mediosPago;
    @Size(max = 14)
    private String telefono;
    @Size(max = 14)
    @NotBlank
    private String movil;
    @NotBlank(message = "Debe seleccionar por lo menos una ficha en la parte final de la pestaña de servicios")
    @Size(max=36, message = "Fichas seleccionadas exceden el tamaño máximo permitido")
    private String fichaClienteIds;
    @Size(max = 100)
    private String mapCoordenadas;
    @Digits(integer = 5, fraction = 2)
    private Double mapCircleRadio;
    @Size(max = 600)
    private String redes;
    @NotBlank
    @Size(max = 30)
    private String disciplinaIds;

    @NotNull
    @Positive
    @Max(value = 3)
    private Integer tipoTrainerId;

    @Size(max = 100)
    private String svcIds;

}
