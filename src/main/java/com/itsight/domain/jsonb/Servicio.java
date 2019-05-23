package com.itsight.domain.jsonb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Servicio implements Serializable {

    @Size(min = 2, max = 50)
    @NotNull
    private String nombre;
    @Size(min = 5, max = 2000)
    @NotNull
    private String descripcion;
    @Size(max = 2000)
    private String incluye;

    @Size(max = 1000)
    private String infoAdicional;

    @Valid
    private List<Tarifario> tarifarios;

}
