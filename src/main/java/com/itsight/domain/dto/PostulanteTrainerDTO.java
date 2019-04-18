package com.itsight.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PostulanteTrainerDTO implements Serializable {

    private String nombreFull;

    private String correo;

    private String movil;

    private String mensaje;
}
