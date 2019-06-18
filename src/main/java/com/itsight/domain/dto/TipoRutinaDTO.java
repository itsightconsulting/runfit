package com.itsight.domain.dto;

import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class TipoRutinaDTO implements Serializable {

    @Positive
    private Integer id;

    @Size(max=250)
    @NotBlank
    private String nombre;

}
