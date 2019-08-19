package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.base.AuditingEntity;
import com.itsight.domain.jsonb.RutinaControl;
import com.itsight.json.JsonDateSimpleDeserializer;
import com.itsight.json.JsonDateSimpleSerializer;
import com.itsight.util.EntityVisitor;
import com.itsight.util.Identifiable;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
@NamedEntityGraphs({
        @NamedEntityGraph(name = "rutinaPlantilla.trainer", attributeNodes = {
                @NamedAttributeNode(value = "trainer")}),
        @NamedEntityGraph(name = "rutinaPlantilla"),
})*/
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RutinaPlantilla extends AuditingEntity implements Identifiable {


    public static EntityVisitor<RutinaPlantilla, BagForest> ENTITY_VISITOR = new EntityVisitor<RutinaPlantilla, BagForest>(RutinaPlantilla.class) {

        @Override
        public BagForest getParent(RutinaPlantilla visitingObject) {
            return visitingObject.getForest();
        }

        @Override
        public List<RutinaPlantilla> getChildren(BagForest parent) {
            return parent.getTreesRp();
        }

        @Override
        public void setChildren(BagForest parent) {
            parent.setTreesRp(new ArrayList<>());
        }
    };


    @Id
    @GeneratedValue(generator = "rutina_plantilla_seq")
    @GenericGenerator(
            name = "rutina_plantilla_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @Parameter(name = "rutina_plantilla_seq", value = "rutina_plantilla_seq"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "RutinaPlantillaId")
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int anios;
    @Column(nullable = false)
    private int meses;
    @Column(nullable = false)
    private int totalSemanas;
    @Column(nullable = false)
    private int dias;

    @Type(type = "jsonb")
    @Column(name = "control", columnDefinition = "jsonb")
    private RutinaControl control;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "rutinaPlantilla", cascade = CascadeType.ALL /*, orphanRemoval = true*/ )
    private List<SemanaPlantilla> lstSemana;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TrainerId", referencedColumnName = "SecurityUserId")
    private Trainer trainer;

    @JsonIgnoreProperties
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "CategoriaPlantillaId" , referencedColumnName =  "CategoriaPlantillaId")
    private CategoriaPlantilla categoriaPlantilla;


    @JsonBackReference
    @ManyToOne
    public BagForest forest;

    public RutinaPlantilla(){}

}


