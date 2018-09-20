package com.itsight.domain.jsonb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Estilo implements Serializable {

    private String id;
    private String clase;

    public Estilo() { }
}
