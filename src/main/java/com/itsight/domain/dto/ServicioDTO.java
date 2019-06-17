package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itsight.domain.jsonb.Tarifario;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServicioDTO implements Serializable {

    @Positive
    private Integer id;
    @Size(min = 2, max = 50)
    @NotNull
    private String nombre;
    @Size(min = 80, max = 2000)
    @NotNull
    private String descripcion;
    @Size(max = 2000)
    private String incluye;
    @Size(min = 20, max = 1000)
    private String infoAdicional;
    @Valid
    private List<Tarifario> tarifarios;

}
