package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteDTO implements Serializable  {
    private Integer trainerId;
    private String nombres;
    private String apellidos;
    private String numeroDocumento;
    private String correo;
    private String correoTrainer;
    private String movil;
    @Past
    private Date fechaNacimiento;
    private String username;
    /*FK's*/
    private Integer tipoDocumentoId;
    private Integer paisId;
    private String ubigeo;
    private String password;

    private Integer servicioId;
    private Integer predetFichaId;
    @Valid
    private ClienteFitnessDTO cliFit;

}
