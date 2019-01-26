package com.itsight.domain.jsonb;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class Parametro implements Serializable {

    @NotNull
    @Size(min = 4)
    private String nombre;
    @NotNull
    @Size(min = 1)
    private String valor;

    public Parametro(){}

    public Parametro(String nombre, String valor) {
        this.nombre = nombre;
        this.valor = valor;
    }
}
