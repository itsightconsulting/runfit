package com.itsight.domain.jsonb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RuGeneral implements Serializable {

    private int freMin;
    private int freMax;
    private int nvAtleta;
    private int distancia;

    private int disControl;
    private String tieControl;
    private int cadControl;
    private int tcsControl;
    private int facDesControl;
    private String tieDesControl;

    private int disCompetencia;
    private String tieCompetencia;
    private int cadCompetencia;
    private int tcsCompetencia;
    private double facMejoria;

}
