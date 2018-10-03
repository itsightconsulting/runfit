package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.base.AuditingEntity;
import com.itsight.domain.jsonb.RutinaControl;
import com.itsight.json.JsonDateSimpleDeserializer;
import com.itsight.json.JsonDateSimpleSerializer;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;


@NamedEntityGraphs({
        @NamedEntityGraph(name = "rutina.usuario", attributeNodes = {
                @NamedAttributeNode(value = "usuario")}),
        @NamedEntityGraph(name = "rutina"),
})
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
        @TypeDef(name = "int-array", typeClass = IntArrayType.class)
})
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rutina extends AuditingEntity {

    @Id
    @GeneratedValue(generator = "rutina_seq")
    @GenericGenerator(
            name = "rutina_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @Parameter(name = "rutina_seq", value = "rutina_seq"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "RutinaId")
    private int id;
    @Column(nullable = false)
    private int anios;
    @Column(nullable = false)
    private int meses;
    @Column(nullable = false)
    private int totalSemanas;
    @Column(nullable = false)
    private int dias;
    @Column(nullable = false)
    private int tipoRutina;
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UsuarioId", referencedColumnName = "SecurityUserId", updatable = false)
    private Usuario usuario;
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RedFitnessId", referencedColumnName = "RedFitnessId", updatable = false)
    private RedFitness redFitness;
    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rutina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Semana> lstSemana;
    @JsonIgnore
    @Type(type = "int-array")
    @Column(name="SemanaIds", columnDefinition = "integer[]")
    private int[] semanaIds;
    @Type(type = "jsonb")
    @Column(name = "control", columnDefinition = "jsonb")
    private RutinaControl control;


    public Rutina(){}

    public Rutina(int id){
        this.id = id;
    }

    public void setUsuario(int userId) {
        this.usuario = new Usuario(userId);
    }

    public void setRedFitness(int redFitnessId){this.redFitness = new RedFitness(redFitnessId);}

}
