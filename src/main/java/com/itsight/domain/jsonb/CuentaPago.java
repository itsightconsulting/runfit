package com.itsight.domain.jsonb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CuentaPago implements Serializable {

    @Positive
    @NotNull
    private Integer bancoId;
    @Size(min= 10,max = 30)
    private String numeroSoles;
    @Size(min= 10,max = 30)
    private String numeroDolares;
    @Size(min= 10,max = 30)
    private String interbancarioSoles;
    @Size(min= 10,max = 30)
    private String interbancarioDolares;
    @NotBlank
    @Size(min= 6,max = 60)
    private String titular;
    @Size(max = 20)
    private String titularTipoDoc;
    @Size(min= 8,max = 13)
    private String titularNumDoc;
}
