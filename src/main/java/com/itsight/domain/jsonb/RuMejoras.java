package com.itsight.domain.jsonb;

import lombok.Data;

import java.io.Serializable;

@Data
public class RuMejoras implements Serializable {

    private int porcGe;
    private int semGe;
    private int porcEs;
    private int semEs;
    private int porcPr;
    private int semPr;

    private int velPorcGe;
    private int velPorcEs;
    private int velPorcPr;

    private int cadPorcGe;
    private int cadPorcEs;
    private int cadPorcPr;

    private int tcsPorcGe;
    private int tcsPorcEs;
    private int tcsPorcPr;
}
