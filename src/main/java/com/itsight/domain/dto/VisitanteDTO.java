package com.itsight.domain.dto;

import com.itsight.validation.ExtendedEmailValidator;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class VisitanteDTO implements Serializable {

    @PositiveOrZero
    private Integer id;

    @Size(max = 40)
    @ExtendedEmailValidator
    @NotBlank
    @NaturalId
    private String correo;

    @Size(max=40)
    @NotBlank
    private String nombres;

    @Size(max=30)
    @NotBlank
    private String apellidos;

    @Size(max=30)
    @NotBlank
    private String password;







}
