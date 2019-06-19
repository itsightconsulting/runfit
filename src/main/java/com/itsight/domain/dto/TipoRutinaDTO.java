package com.itsight.domain.dto;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Id;
import javax.validation.constraints.*;
import java.io.Serializable;

@Data
public class TipoRutinaDTO implements Serializable {

    @PositiveOrZero
    private Integer id;

    @Size(max=100)
    @NotBlank
    private String nombre;

    @NotNull
    private Boolean flagActivo;


}
