package com.itsight.domain.jsonb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CuentaPago implements Serializable {

    private Integer bancoId;
    private String numeroSoles;
    private String numeroDolares;
    private String interbancarioSoles;
    private String interbancarioDolares;
    private String titular;
    private String titularTipoDoc;
    private String titularNumDoc;
}
