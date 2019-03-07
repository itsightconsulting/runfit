package com.itsight.domain.jsonb;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CondicionAnatomica implements Serializable {

    private boolean flagPadeceDolor;
    private String descripcionDolor;
    private String momDolor;
    private String momDolorIni;
    private boolean flagLesionSeisUltimosMeses;
    private String desLesion;
    private String recomMedica;
    private boolean flagEmbarazo;

    //

    private int frecuenciaCardiaca;
    private int frecuenciaCardiacaMaxima;
    private int intensidad;
    private int formaInicial;
    private List<Musculo> musculosSensibles;

}
