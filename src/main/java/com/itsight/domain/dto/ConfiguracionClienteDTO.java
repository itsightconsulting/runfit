package com.itsight.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConfiguracionClienteDTO implements Serializable {

    private String nombre;

    private String valor;

    public ConfiguracionClienteDTO() {}

    public ConfiguracionClienteDTO(String nombre, String valor) {
        this.nombre = nombre;
        this.valor = valor;
    }
}
