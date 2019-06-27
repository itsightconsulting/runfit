package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.base.AuditingEntity;
import com.itsight.domain.dto.RutinaDTO;
import com.itsight.domain.jsonb.RutinaControl;
import com.itsight.domain.pojo.RuCliPOJO;
import com.itsight.domain.pojo.RutinaPOJO;
import com.itsight.json.JsonDateSimpleDeserializer;
import com.itsight.json.JsonDateSimpleSerializer;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.*;
import org.springframework.data.domain.Sort;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "getLastByClienteId",
                classes = {
                        @ConstructorResult(
                                targetClass = RuCliPOJO.class,
                                columns = {
                                        @ColumnResult(name = "id"),
                                        @ColumnResult(name = "anios"),
                                        @ColumnResult(name = "meses"),
                                        @ColumnResult(name = "totalSemanas"),
                                        @ColumnResult(name = "dias"),
                                        @ColumnResult(name = "tipoRutinaId"),
                                        @ColumnResult(name = "fechaInicio"),
                                        @ColumnResult(name = "fechaFin"),
                                        @ColumnResult(name = "control"),
                                        @ColumnResult(name = "rows"),
                                }

                        )
                }
        )
})
@NamedEntityGraphs({
        @NamedEntityGraph(name = "rutina.cliente", attributeNodes = {
                @NamedAttributeNode(value = "cliente")}),
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
    private Integer id;
    @Column(nullable = false)
    private int anios;
    @Column(nullable = false)
    private int meses;
    @Column(nullable = false)
    private int totalSemanas;
    @Column(nullable = false)
    private int dias;

    //ManytoOne
    //Generatedvalue +
    // id y nombre
    //
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TipoRutinaId", referencedColumnName = "TipoRutinaId")
    private TipoRutina tipoRutina;
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    @Temporal(TemporalType.DATE)
    private Date fechaCliAcceso;
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
    @JoinColumn(name = "ClienteId", referencedColumnName = "SecurityUserId", updatable = false)
    private Cliente cliente;
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
    private Integer[] semanaIds;
    @Type(type = "jsonb")
    @Column(name = "control", columnDefinition = "jsonb")
    private RutinaControl control;

    @Transient
    private List<Semana> listaSemanas;

    public Rutina(){}

    public Rutina(Integer id){
        this.id = id;
    }

    public void setCliente(Integer cliId) {
        this.cliente = new Cliente(cliId);
    }

    public void setRedFitness(Integer redFitnessId){this.redFitness = new RedFitness(redFitnessId);}

    public void setTipoRutina(Integer tipoRutinaId) {
        this.tipoRutina = new TipoRutina(tipoRutinaId);
    }
}
