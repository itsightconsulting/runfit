package com.itsight.domain.jsonb;

import lombok.Data;

import java.io.Serializable;

@Data
public class Salud implements Serializable
 {

    private Boolean flagEnfermedad;
    private Boolean flagFumador;
    private Boolean flagHiperTensionArt;
    private Boolean flagAntFamInfOrHiper;
    private Boolean flagResInsulina;
    private Boolean flagAntFamDiabetes;
    private Boolean flagSobrepeso;
    private Boolean flagAleMed;
    private Boolean flagOperacion;
    private Boolean flagHerDisOrCondCol;
    private Integer famDiabetico;
    private Integer famInfOrHiper;
    private Integer catSobrepeso;
    private Integer catAlimentacion;
    private Integer nivelEstres;
    private String desEnfermedad;
    private String desFumador;
    private String desAleMed;
    private String desOperacion;
    private String desHerDisOrCondCol;
    private String desPadVarios;
    private String contactosEmergencia;
    private String historialDeportivoAnt;
    private String historialDeportivoAct;
}
