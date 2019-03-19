package com.itsight.domain.jsonb;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CondicionAnatomica implements Serializable {


    private Boolean flagPadeceDolor;
    private String descripcionDolor;
    private String momDolor;
    private String momDolorIni;
    private Boolean flagLesionSeisUltimosMeses;
    private String desLesion;
    private String recomMedica;
    private Boolean flagEmbarazo;

    //

    private Integer frecuenciaCardiaca;
    private Integer frecuenciaCardiacaMaxima;
    private Integer intensidad;
    private Integer formaInicial;
    private List<Musculo> musculosSensibles;

}
