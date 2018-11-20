package com.itsight.domain.jsonb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RuStats implements Serializable {
    private String ritmoXkm;
    private String ritCompActual;
    private String ritAerActual;
    private String ritAerPreComp;
    private double lonPasoCompActual;
    private double kilometrajeTotal;
    private double kilometrajeProm;
    private String pasoSubida;
    private String pasoBajada;
    private String pasoPlano;
    private String rcps;
    private String raps;
    private String cdcs;
    private String lpcs;
}
