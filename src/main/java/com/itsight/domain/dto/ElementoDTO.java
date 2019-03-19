package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.itsight.domain.jsonb.Estilo;
import com.itsight.domain.jsonb.SubElemento;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElementoDTO implements Serializable {

    private String nombre;
    private String nota;
    private String mediaAudio;
    private String mediaVideo;
    private int minutos;
    private int distancia;
    private Set<Estilo> estilos;
    private List<SubElemento> subElementos;
    private int tipo;
    @JsonInclude(Include.NON_DEFAULT)
    private int tipoMedia;
    @JsonInclude(Include.NON_DEFAULT)
    private int numeroSemana;
    @JsonInclude(Include.NON_DEFAULT)
    private int diaIndice;
    @JsonInclude(Include.NON_DEFAULT)
    private int elementoIndice;

    public ElementoDTO(){}
}
