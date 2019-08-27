package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.jsonb.Elemento;
import com.itsight.domain.jsonb.ListaPlantilla;
import com.itsight.json.JsonDateSimpleDeserializer;
import com.itsight.json.JsonDateSimpleSerializer;
import com.itsight.util.EntityVisitor;
import com.itsight.util.Identifiable;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "diaPlantilla"),
})
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Data
@Entity
public class DiaPlantilla implements Identifiable {

    public static EntityVisitor<DiaPlantilla, SemanaPlantilla> ENTITY_VISITOR = new EntityVisitor<DiaPlantilla, SemanaPlantilla>(DiaPlantilla.class) {
        @Override
        public SemanaPlantilla getParent(DiaPlantilla visitingObject) {
            return visitingObject.getSemanaPlantilla();
        }

        @Override
        public List<DiaPlantilla> getChildren(SemanaPlantilla parent) {
            return parent.getLstDiaPlantilla();
        }

        @Override
        public void setChildren(SemanaPlantilla parent) {
            parent.setLstDiaPlantilla(new ArrayList<>());
        }
    };


    @Id
    @GeneratedValue(generator = "dia_plantilla_seq")
    @GenericGenerator(
            name = "dia_plantilla_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @Parameter(name = "dia_plantilla_seq", value = "dia_plantilla_seq"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "DiaPlantillaId")
    private Integer id;
    @Column
    private int dia;
    @Column
    private String literal;
    @Column
    private Boolean flagDescanso;

    @Column
    private String smsHeader;
    @Column
    private String nota;
    @Column
    private String voz;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SemanaPlantillaId")
    private SemanaPlantilla semanaPlantilla;


    @Column
    private int minutos;

    @Column
    private double distancia;

    @Column
    private double calorias;

    @Column
    private int esfuerzoSemanal;

    @Column
    private int avanceSemanal;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Elemento> elementos;

    @Column
    private Boolean flagEnvioCliente;

    public DiaPlantilla(){}

    public String getDiaLiteral(){
        return this.literal + " " + this.dia;
    }
}
