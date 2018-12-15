package com.itsight.domain.jsonb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Estilo implements Serializable {

    private int id;
    @Size(max = 40)
    private String clase;
    private int tipo;

    public Estilo() { }
}
