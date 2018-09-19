package com.itsight.domain.jsonb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("elementos")
public class ElementoPlantilla implements Serializable {

    private String nombre;
    private String duracion;
    private String media;
    private Set<Estilo> estilos;
    private int tipo;
    @JsonInclude(Include.NON_DEFAULT)
    private int numeroSemana;
    @JsonInclude(Include.NON_DEFAULT)
    private int diaIndice;
    @JsonInclude(Include.NON_DEFAULT)
    private int listaIndice;
    @JsonInclude(Include.NON_DEFAULT)
    private int elementoIndice;

    public ElementoPlantilla(){}

    public ElementoPlantilla(String nombre, String duracion, String media, int tipo){
        this.nombre = nombre;
        this.duracion = duracion;
        this.media = media;
        this.tipo = tipo;
    }
}
