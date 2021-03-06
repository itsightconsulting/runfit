package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.itsight.domain.jsonb.Estilo;
import com.itsight.domain.jsonb.SubElemento;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElementoEspecifico implements Serializable {

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
    private int numeroSemana;
    @JsonInclude(Include.NON_DEFAULT)
    private int diaIndice;
    @JsonInclude(Include.NON_DEFAULT)
    private int elementoIndice;
    @JsonInclude(Include.NON_DEFAULT)
    private int subElementoIndice;
    @JsonInclude(Include.NON_DEFAULT)
    private Integer refElementoIndice;
    @JsonInclude(Include.NON_DEFAULT)
    private Boolean insertarDespues;

    public ElementoEspecifico(){}

    public ElementoEspecifico(String nombre, int tipo){
        this.nombre = nombre;
        this.tipo = tipo;
    }
}
