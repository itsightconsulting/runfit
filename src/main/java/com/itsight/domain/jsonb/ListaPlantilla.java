package com.itsight.domain.jsonb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListaPlantilla implements Serializable {

    private String nombre;
    private List<ElementoPlantilla> elementos;
    private List<Estilo> estilos;
    @JsonInclude(Include.NON_DEFAULT)
    private int numeroSemana;
    @JsonInclude(Include.NON_DEFAULT)
    private int diaIndice;
    @JsonInclude(Include.NON_DEFAULT)
    private int listaIndice;
    public ListaPlantilla(){}

    public ListaPlantilla(String nombre) {
        this.nombre = nombre;
    }
}
