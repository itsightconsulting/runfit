package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

@Entity
@Data
@NamedEntityGraphs({
    @NamedEntityGraph(name = "ruConsolidado"),
    @NamedEntityGraph(name = "ruConsolidado.rutina", attributeNodes = {
        @NamedAttributeNode(value = "rutina")
    })
})
@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class RuConsolidado implements Serializable {

    @Id
    private Integer id;
    @Column(columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private RuGeneral general;
    @Column(columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private RuStats stats;
    @Column(columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private RuMejoras mejoras;
    @Column(nullable = false, columnDefinition="TEXT")
    private String matrizMejoraVelocidades;
    @Column(nullable = false)
    private String matrizMejoraCadencia;
    @Column(nullable = false)
    private String matrizMejoraTcs;
    @Column(nullable = false)
    private String matrizMejoraLonPaso;
    @Column(columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private List<RuDataGrafico> dtGrafico;
    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "RutinaId")
    private Rutina rutina;

    public RuConsolidado(){}

    public RuConsolidado(RuGeneral general, RuStats stats, RuMejoras mejoras, String matrizMejoraVelocidades, String matrizMejoraCadencia, String matrizMejoraTcs, String matrizMejoraLonPaso, List<RuDataGrafico> dtGrafico, Rutina rutina) {
        this.general = general;
        this.stats = stats;
        this.mejoras = mejoras;
        this.matrizMejoraVelocidades = matrizMejoraVelocidades;
        this.matrizMejoraCadencia = matrizMejoraCadencia;
        this.matrizMejoraTcs = matrizMejoraTcs;
        this.matrizMejoraLonPaso = matrizMejoraLonPaso;
        this.dtGrafico = dtGrafico;
        this.rutina = rutina;
    }
}
