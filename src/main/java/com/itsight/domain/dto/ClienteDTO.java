package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteDTO implements Serializable  {

    private String nombres;
    private String apellidos;
    private String numeroDocumento;
    private String correo;
    private String movil;
    private Date fechaNacimiento;
    private String username;
    /*FK's*/
    private Integer tipoDocumentoId;
    private Integer paisId;
    private String ubigeo;
    private String password;
    @Valid
    private ClienteFitnessDTO cliFit;

}
