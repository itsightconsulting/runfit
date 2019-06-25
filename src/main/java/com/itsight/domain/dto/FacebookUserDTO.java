package com.itsight.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonDateSimpleSerializer;
import com.itsight.validation.ExtendedEmailValidator;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

public class FacebookUserDTO {


    @PositiveOrZero
    Integer id;

    @NotNull
    Integer facebookId;

    @NotNull
    String primerNombre;

    @NotNull
    String segundoNombre;

    @NotNull
    Date fechaNacimiento;

    @NotNull
    Integer genero;

    @NotNull
    String locacion;

    @Size(max = 40)
    String email;

}
