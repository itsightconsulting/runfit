package com.itsight.domain.jsonb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("subElementos")
public class SubElemento implements Serializable {

    private String nombre;
    private String nota;
    private String mediaAudio;
    private String mediaVideo;
    private Set<Estilo> estilos;
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
    private double distancia;
    @JsonInclude(Include.NON_DEFAULT)
    private double distanciaDia;
    @JsonInclude(Include.NON_DEFAULT)
    private double calorias;
    @JsonInclude(Include.NON_DEFAULT)
    private int minutos;

    public SubElemento(){}

    public SubElemento(String nombre, int tipo){
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public SubElemento(String nombre, String mediaAudio, String mediaVideo, int tipo){
        this.nombre = nombre;
        this.mediaAudio = mediaAudio;
        this.mediaVideo = mediaVideo;
        this.tipo = tipo;
    }
}
