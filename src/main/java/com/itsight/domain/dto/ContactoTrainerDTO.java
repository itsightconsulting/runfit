package com.itsight.domain.dto;

import com.itsight.validation.ExtendedEmailValidator;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class ContactoTrainerDTO implements Serializable {

    @Size(min=5, max = 60)
    @NotBlank
    private String nombre;

    @Size(min=7, max = 40)
    @ExtendedEmailValidator
    @NotBlank
    private String correo;

    @Size(min=7, max = 13)
    @NotBlank
    private String movil;

    @Size(min = 5, max = 1000)
    @NotBlank
    private String mensaje;

    @NotNull
    @Positive
    private Integer sexo;

    @Size(max = 40)
    @ExtendedEmailValidator
    @NotBlank
    private String correoTrainer;
}
