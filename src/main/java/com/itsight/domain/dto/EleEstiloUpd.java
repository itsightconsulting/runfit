package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itsight.domain.jsonb.Estilo;
import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class EleEstiloUpd implements Serializable {

    private int diaIndice;
    private int elementoIndice;
    private int subElementoIndice;
    private int numeroSemana;
    @Valid
    private Set<Estilo> estilos;

}
