package com.itsight.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.dto.RedFitCliDTO;
import com.itsight.json.JsonDateSimpleDeserializer;
import com.itsight.json.JsonDateSimpleSerializer;
import com.itsight.util.Enums.EstadoPlan;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import javax.validation.Constraint;
import java.io.Serializable;
import java.util.Date;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "redFitness"),
        @NamedEntityGraph(name = "redFitness.cliente",
                attributeNodes = {
                        @NamedAttributeNode(value = "cliente")
                }),
})
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "ClienteId",
                "TrainerId"
        })
})
@Data
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
})
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "allRedFitnessByTrainerId",
                        classes = {
                            @ConstructorResult(
                                targetClass = RedFitCliDTO.class,
                                columns = {
                                        @ColumnResult(name = "id"),
                                        @ColumnResult(name = "nota"),
                                        @ColumnResult(name = "msgCliente"),
                                        @ColumnResult(name = "contadorRutinas"),
                                        @ColumnResult(name = "estadoPlan"),
                                        @ColumnResult(name = "fechaInicialPlanificacion"),
                                        @ColumnResult(name = "fechaFinalPlanificacion"),
                                        @ColumnResult(name = "cliNombreCompleto"),
                                        @ColumnResult(name = "cliMovil"),
                                        @ColumnResult(name = "cliFechaUltimoAcceso"),
                                        @ColumnResult(name = "cliFechaNacimiento"),
                                        @ColumnResult(name = "cliId"),
                                        @ColumnResult(name = "cliCorreo"),
                                        @ColumnResult(name = "predeterminadaFichaId"),
                                        @ColumnResult(name = "rows")
                                }
                            )

                        })
})

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "fn_validacion_exists_by_trainer_id_and_cliente_id",
                procedureName = "check_red_fitness_exist_by_trainer_id_and_cliente_id",
                parameters = {
                        @StoredProcedureParameter(name = "_trainer_id", mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "_cliente_id", mode = ParameterMode.IN, type = Integer.class),
                        @StoredProcedureParameter(name = "result", mode = ParameterMode.OUT, type = Boolean.class)
                })
})
public class RedFitness implements Serializable {

    @Id
    @GeneratedValue(generator = "red_fitness_seq")
    @GenericGenerator(
            name = "red_fitness_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @Parameter(name = "red_fitness_seq", value = "red_fitness_seq"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "RedFitnessId")
    private Integer id;
    @Column(nullable = false)
    private int contadorRutinas;
    @Column(nullable = false)
    private int estadoPlan;
    @Column
    private String nota;
    @Column
    private String msgCliente;
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    @Temporal(TemporalType.DATE)
    private Date ultimoDiaTemporada;
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClienteId", referencedColumnName = "SecurityUserId")
    private Cliente cliente;
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TrainerId", referencedColumnName = "SecurityUserId")
    private Trainer trainer;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using=JsonDateSimpleSerializer.class)
    @JsonDeserialize(using=JsonDateSimpleDeserializer.class)
    private Date fechaCreacion;
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using=JsonDateSimpleSerializer.class)
    @JsonDeserialize(using=JsonDateSimpleDeserializer.class)
    private Date fechaInicialPlanificacion;
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using=JsonDateSimpleSerializer.class)
    @JsonDeserialize(using=JsonDateSimpleDeserializer.class)
    private Date fechaFinalPlanificacion;
    @Column(nullable = false)
    private Integer predeterminadaFichaId;
    @Column(nullable = false)
    private boolean flagActivo;
    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "redFitness", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Chat chat;

    @Temporal(TemporalType.DATE)
    @JsonSerialize(using=JsonDateSimpleSerializer.class)
    @JsonDeserialize(using=JsonDateSimpleDeserializer.class)
    private Date fechaSuspension;

    public RedFitness(){
    }

    public RedFitness(Integer id){
        this.id = id;
    }

    public RedFitness(Integer trainerId, Integer clienteId, String msgCliente){
        this.trainer = new Trainer(trainerId);
        this.cliente = new Cliente(clienteId);
        this.msgCliente = msgCliente;
        this.fechaCreacion = new Date();
        this.estadoPlan = EstadoPlan.SIN_PLAN.get();
    }
}
