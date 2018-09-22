package com.itsight.domain.jsonb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Estilo implements Serializable {

    private int id;
    private String clase;
    private int tipo;

    public Estilo() { }
}
