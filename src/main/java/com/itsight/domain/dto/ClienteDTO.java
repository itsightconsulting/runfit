package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonDateSimpleDeserializer;
import com.itsight.json.JsonDateSimpleSerializer;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteDTO implements Serializable  {
    private Integer trainerId;
    private String nombres;
    private String apellidos;
    private String numeroDocumento;
    private String correo;
    private String correoTrainer;
    private String movil;
    @Past
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date fechaNacimiento;
    private String username;
    /*FK's*/
    private Integer tipoDocumentoId;
    private Integer paisId;
    private String ubigeo;
    private String password;

    private Integer servicioId;
    private Integer predetFichaId;
    @Valid
    private ClienteFitnessDTO cliFit;

    private String departamentoUb;
    private BigInteger qtyClientesByDepartamento;



}
