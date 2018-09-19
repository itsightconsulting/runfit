package com.itsight.domain.jsonb;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CondicionAnatomica implements Serializable {

    private boolean flagPadeceDolor;
    private String descripcionDolor;
    private String momentoDelDolor;
    private boolean flagLesionSeisUltimosMeses;
    private String descripcionLesion;
    private String recomendacionesMedicas;
    private boolean flagEmbarazo;

    //

    private int frecuenciaCardiaca;
    private int frecuenciaCardiacaMaxima;
    private int intensidad;
    private List<Musculo> musculosSensibles;


}
