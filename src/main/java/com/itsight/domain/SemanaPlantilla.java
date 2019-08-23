package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonDateSimpleDeserializer;
import com.itsight.json.JsonDateSimpleSerializer;
import com.itsight.util.EntityVisitor;
import com.itsight.util.Identifiable;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "semanaPlantilla")
})
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Data
@Entity
public class SemanaPlantilla implements Identifiable {

    public static EntityVisitor<SemanaPlantilla, RutinaPlantilla> ENTITY_VISITOR = new EntityVisitor<SemanaPlantilla, RutinaPlantilla>(SemanaPlantilla.class) {
        @Override
        public RutinaPlantilla getParent(SemanaPlantilla visitingObject) {
            return visitingObject.getRutinaPlantilla();
        }

        @Override
        public List<SemanaPlantilla> getChildren(RutinaPlantilla parent) {
            return parent.getLstSemana();
        }

        @Override
        public void setChildren(RutinaPlantilla parent) {
            parent.setLstSemana(new ArrayList<>());
        }
    };



    @Id
    @GeneratedValue(generator = "semana_plantilla_seq")
    @GenericGenerator(
            name = "semana_plantilla_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @Parameter(name = "semana_plantilla_seq", value = "semana_plantilla_seq"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "SemanaPlantillaId")
    private Integer id;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @Column
    private Boolean flagFull;

    @Column(nullable = false)
    private double kilometrajeTotal;

    @Column(nullable = false)
    private double kilometrajeActual;

    @Column(nullable = false)
    private double calorias;

    @Column(nullable = false)
    private double horas;

    @Column
    private String objetivos;

    @Column(columnDefinition="TEXT")
    private String metricas;

    @Column(columnDefinition = "text")
    private String metricasVelocidad;

    @Column(nullable = true)
    private String prioridad;

    @Column
    private Boolean flagEnvioCliente;


    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RutinaPlantillaId")
    private RutinaPlantilla rutinaPlantilla;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "semanaPlantilla", cascade = CascadeType.ALL)
    private List<DiaPlantilla> lstDiaPlantilla;

    public SemanaPlantilla(){}


}
