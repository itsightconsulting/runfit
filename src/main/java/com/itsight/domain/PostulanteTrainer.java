package com.itsight.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonDateSimpleDeserializer;
import com.itsight.json.JsonDateSimpleSerializer;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class PostulanteTrainer {

    @Id
    @GeneratedValue(generator = "pre_trainer_seq")
    @GenericGenerator(
            name = "pre_trainer_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @org.hibernate.annotations.Parameter(name = "pre_trainer_seq", value = "pre_trainer_seq"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "PreTrainerId")
    private Integer id;

    @Column(nullable = false)
    private String nombreFull;

    @Column(unique = true, nullable = false, updatable = false)
    private String correo;

    @Column(nullable = false)
    private String movil;

    @Column(nullable = false)
    private String mensaje;

    @Column(nullable = false)
    private boolean flagRechazado;

    @Column(nullable = false)
    private boolean flagAceptado;

    @Column(nullable = false)
    private boolean flagRegistrado;

    @Column(nullable = false)
    private int intentos;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date fechaCreacion;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date fechaRechazo;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date fechaAprobacion;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date fechaLimiteAccion;

}
