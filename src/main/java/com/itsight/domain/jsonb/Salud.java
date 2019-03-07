package com.itsight.domain.jsonb;

import lombok.Data;

import java.io.Serializable;

@Data
public class Salud implements Serializable
 {

    private boolean flagEnfermedad;
    private boolean flagFumador;
    private boolean flagHiperTensionArt;
    private boolean flagAntFamInfOrHiper;
    private boolean flagResInsulina;
    private boolean flagAntFamDiabetes;
    private boolean flagSobrepeso;
    private boolean flagAleMed;
    private boolean flagOperacion;
    private boolean flagHerDisOrCondCol;
    private int famDiabetico;
    private int famInfOrHiper;
    private int catSobrepeso;
    private int catAlimentacion;
    private int nivelEstres;
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
