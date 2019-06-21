package com.itsight.domain.dto;

import com.itsight.validation.ExtendedEmailValidator;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
public class PerfilObsDTO implements Serializable {


    @NotBlank
    @Size(max = 32)
    private String hshTrainerId;
    @ExtendedEmailValidator
    @NotBlank
    @Size(max = 60)
    private String correo;
    @NotBlank
    @Size(min = 10, max = 1000)
    private String obs;

    @Positive
    @Max(3)
    @NotNull
    private Integer tipoTrainerId;
}
