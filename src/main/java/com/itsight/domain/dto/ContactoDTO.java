package com.itsight.domain.dto;

import com.itsight.validation.ExtendedEmailValidator;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class ContactoDTO implements Serializable {

    @Size(max = 100)
    @NotBlank
    private String nombre;

    @Size(max = 40)
    @ExtendedEmailValidator
    @NotBlank
    private String correo;

    @Size(max = 16)
    @NotBlank
    private String movil;

    @Size(min = 5, max = 1000)
    @NotBlank
    private String mensaje;
}
