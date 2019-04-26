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
    @NotBlank
    @Size(min= 10,max = 40)
    private String numeroSoles;
    @NotBlank
    @Size(min= 10,max = 40)
    private String numeroDolares;
    @NotBlank
    @Size(min= 10,max = 50)
    private String interbancarioSoles;
    @NotBlank
    @Size(min= 10,max = 50)
    private String interbancarioDolares;
    @NotBlank
    @Size(min= 8,max = 80)
    private String titular;
    @NotBlank
    @Size(max = 40)
    private String titularTipoDoc;
    @NotBlank
    @Size(min= 8,max = 16)
    private String titularNumDoc;
}
