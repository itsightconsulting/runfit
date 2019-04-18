package com.itsight.domain.jsonb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CuentaPago implements Serializable {

    private Integer bancoId;
    private Integer monedaId;
    private String numero;
    private String interbancario;
    private String titular;
    private String titularTipoDoc;
    private String titularNumDoc;
}
