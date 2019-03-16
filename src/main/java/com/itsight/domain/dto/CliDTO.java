package com.itsight.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CliDTO implements Serializable  {

    private String nombres;
    private String apellidos;
    private String numeroDocumento;
    private String correo;
    private String movil;
    private Date fechaNacimiento;
    private String username;
    /*private String */

}
