package com.itsight.domain.jsonb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("elementos")
public class Elemento implements Serializable {

    private String nombre;
    private String nota;
    private String mediaAudio;
    private String mediaVideo;
    private int minutos;
    private double distancia;
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
    private Boolean insertarAntes;
    @JsonInclude(Include.NON_DEFAULT)
    private int minutosDia;
    @JsonInclude(Include.NON_DEFAULT)
    private double distanciaDia;
    @JsonInclude(Include.NON_DEFAULT)
    private double calorias;

    public Elemento(){}

    public Elemento(String nombre, int tipo){
        this.nombre = nombre;
        this.tipo = tipo;
    }
}
