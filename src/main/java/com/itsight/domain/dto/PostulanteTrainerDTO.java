package com.itsight.domain.dto;

import com.itsight.validation.ExtendedEmailValidator;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class PostulanteTrainerDTO implements Serializable {

    @Size(max = 100)
    private String nombreFull;

    @Size(max = 40)
    @ExtendedEmailValidator
    private String correo;

    @Size(max = 16)
    private String movil;

    @Size(min = 5, max = 1000)
    private String mensaje;
}
