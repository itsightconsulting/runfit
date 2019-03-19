package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.base.AuditingEntity;
import com.itsight.json.JsonDateSimpleDeserializer;
import com.itsight.json.JsonDateSimpleSerializer;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@NamedEntityGraphs({
        @NamedEntityGraph(name = "rutinaPlantilla.trainer", attributeNodes = {
                @NamedAttributeNode(value = "trainer")}),
        @NamedEntityGraph(name = "rutinaPlantilla"),
})
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RutinaPlantilla extends AuditingEntity {

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
    private Long id;
    @Column(nullable = false)
    private int anios;
    @Column(nullable = false)
    private int meses;
    @Column(nullable = false)
    private int totalSemanas;
    @Column(nullable = false)
    private int dias;
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
    @JoinColumn(name = "TrainerId", referencedColumnName = "SecurityUserId")
    private Trainer trainer;
    @JsonIgnoreProperties
    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rutinaPlantilla", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SemanaPlantilla> lstSemana;

    public RutinaPlantilla(){}

    public void setTrainer(Long trainerId) {
        this.trainer = new Trainer(trainerId);
    }

}
