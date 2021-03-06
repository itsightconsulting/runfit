package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonMoneyDoubleSimpleSerializer;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainerQueryDTO implements Serializable {

    @Size(max = 20)
    private String idiomas;
    @Size(max = 14)
    private String niveles;
    @Size(min = 2, max = 20)
    private String formasTrabajo;
    @Size(max = 40)
    private String nombres;
    @Size(max = 40)
    private String acerca;
    @Positive
    @Max(value = 3)
    private Integer sexo;
    @Size(max = 30)
    private String ubigeo;
    @Size(min = 2, max = 30)
    private String servicio;
    @Positive
    @Max(100)
    private Integer limit;
    @PositiveOrZero
    private Integer offset;
    @PositiveOrZero
    @Max(5)
    private Double valoracion;

}
