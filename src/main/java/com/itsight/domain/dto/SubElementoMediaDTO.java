package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubElementoMediaDTO implements Serializable {

    private String nombre;
    private String mediaAudio;
    private String mediaVideo;
    private int tipo;
    @JsonInclude(Include.NON_DEFAULT)
    private int tipoMedia;
    @JsonInclude(Include.NON_DEFAULT)
    private int numeroSemana;
    @JsonInclude(Include.NON_DEFAULT)
    private int diaIndice;
    @JsonInclude(Include.NON_DEFAULT)
    private int elementoIndice;
    @JsonInclude(Include.NON_DEFAULT)
    private int subElementoIndice;

    public SubElementoMediaDTO(){}
}
