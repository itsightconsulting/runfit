package com.itsight.domain.jsonb;

import lombok.Data;

import java.io.Serializable;

@Data
public class CuentaPago implements Serializable {

    private Integer tipoId;
    private Integer bancoId;
    private Integer monedaId;
    private String numero;
    private String interbancario;
    private String titular;
    private String documentoTitular;
}
