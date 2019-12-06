package com.itsight.domain.pojo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itsight.domain.Rutina;
import com.itsight.domain.jsonb.RuDataGrafico;
import com.itsight.domain.jsonb.RuGeneral;
import com.itsight.domain.jsonb.RuMejoras;
import com.itsight.domain.jsonb.RuStats;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
public class RuConsolidadoPOJO implements Serializable {

    private Integer id;
    private RuGeneral general;
    private RuStats stats;
    private RuMejoras mejoras;
    private String matrizMejoraVelocidades;
    private String matrizMejoraCadencia;
    private String matrizMejoraTcs;
    private String matrizMejoraLonPaso;
    private List<RuDataGrafico> dtGrafico;
    private Rutina rutina;

    public RuConsolidadoPOJO(){}

    public RuConsolidadoPOJO( RuMejoras mejoras) {

        this.mejoras = mejoras;
    }
}
