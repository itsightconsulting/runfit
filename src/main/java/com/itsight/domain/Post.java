package com.itsight.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itsight.domain.base.AuditingEntity;
import com.itsight.domain.jsonb.PostDetalle;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;


@NamedEntityGraphs({
    @NamedEntityGraph(name = "post", attributeNodes = {}),
    @NamedEntityGraph(name = "post.trainer", attributeNodes = {
        @NamedAttributeNode(value = "trainer")
    })
})
@TypeDefs({
    @TypeDef(name="jsonb", typeClass = JsonBinaryType.class)
})
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Post extends AuditingEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "post_seq")
    @GenericGenerator(
        name = "post_seq",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
            @Parameter(name = "prefer_sequence_per_entity", value = "true"),
            @Parameter(name = "post_seq", value = "post_seq"),
            @Parameter(name = "initial_value", value = "1"),
            @Parameter(name = "increment_size", value = "1")
        }
    )
    @Column(name = "PostId")
    private Integer id;

    @Column(nullable = false, updatable = false)
    private int tipo;

    @Column(nullable = false)
    @Size(min = 1, max = 255)
    private String titulo;

    @Column(columnDefinition="TEXT")
    @Size(max = 7000)
    private String descripcion;

    @Column(updatable = false)
    private UUID uuid;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column
    private String peso;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column
    private String duracion;

    @Column()
    private String rutaWeb;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TrainerId", referencedColumnName = "SecurityUserId", updatable = false)
    private Trainer trainer;

    @Type(type = "jsonb")
    @Column(name = "Detalle", columnDefinition = "jsonb")
    private List<PostDetalle> lstDetalle;

    public Post(){}

    public Post(int tipo, String titulo, String descripcion, UUID uuid, String peso, String duracion, String rutaWeb, List<PostDetalle> lstDetalle) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.uuid = uuid;
        this.peso = peso;
        this.duracion = duracion;
        this.rutaWeb = rutaWeb;
        this.lstDetalle = lstDetalle;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(int trainerId) {
        this.trainer = new Trainer(trainerId);
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }
}
