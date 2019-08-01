package com.itsight.domain.dto;

import com.itsight.validation.ExtendedEmailValidator;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class PostulanteTrainerDTO implements Serializable {

    @Size(max = 60)
    @NotBlank
    private String nombreFull;

    @Size(max = 40)
    @ExtendedEmailValidator
    @NotBlank
    private String correo;

    @Size(max = 13)
    @NotBlank
    private String movil;

    @Size(min = 4, max = 1000)
    @NotBlank
    private String mensaje;

    @Positive
    @Max(3)
    private Integer tipoTrainerId;
}
