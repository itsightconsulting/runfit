package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.jsonb.Elemento;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiaDTO implements Serializable {

    private int minutos;

    private double distancia;

    private double calorias;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int numeroSemana;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int diaIndice;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Elemento> elementos;

}
