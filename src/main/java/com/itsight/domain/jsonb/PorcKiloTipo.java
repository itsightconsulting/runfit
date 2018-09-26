package com.itsight.domain.jsonb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PorcKiloTipo implements Serializable {

    private int tipo;
    private List<PorcKiloTipoSema> semanas;

}
