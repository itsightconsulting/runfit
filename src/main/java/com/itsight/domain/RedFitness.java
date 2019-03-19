package com.itsight.domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
@Data
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
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
    private Long id;
    @Column(nullable = false)
    private int contadorRutinas;
    @Column(nullable = false)
    private int estadoPlan;
    @Column
    private String nota;
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
    private Date fechaFinalPlanificacion;

    public RedFitness(){
    }

    public RedFitness(Long id){
        this.id = id;
    }

    public RedFitness(Long trainerId, Long clienteId){
        this.trainer = new Trainer(trainerId);
        this.cliente = new Cliente(clienteId);
        this.fechaCreacion = new Date();
        this.estadoPlan = EstadoPlan.SIN_PLAN.get();
    }
}
