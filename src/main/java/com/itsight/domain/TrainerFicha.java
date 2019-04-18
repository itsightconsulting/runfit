package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.jsonb.CuentaPago;
import com.itsight.domain.jsonb.Servicio;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "trainerFicha.trainer",
                attributeNodes = {
                        @NamedAttributeNode(value = "trainer")
                }),
        @NamedEntityGraph(name = "trainerFicha")
})
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Entity
@Data
public class TrainerFicha implements Serializable {

    @Id
    @GeneratedValue(generator = "trainer_ficha_seq")
    @GenericGenerator(
            name = "trainer_ficha_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @org.hibernate.annotations.Parameter(name = "trainer_ficha_seq", value = "trainer_ficha_seq"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            })
    @Column(name="TrainerFichaId")
    private Integer id;
    @Column(nullable = false)
    private String especialidad;
    @Column(nullable = false)
    private Integer disciplinaId;
    @Lob
    @Column(nullable = false)
    private String acerca;
    @Column(nullable = false)
    private String idiomas;
    @Lob
    @Column(nullable = false)
    private String estudios;
    @Lob
    @Column(nullable = false)
    private String metodoTrabajo;
    @Lob
    @Column(nullable = false)
    private String experiencias;
    @Lob
    @Column(nullable = false)
    private String resultados;
    @Column(nullable = false)
    private String niveles;
    @Column(nullable = false)
    private String centroTrabajo;
    @Column(nullable = false)
    private String especialidades;
    @Column(nullable = false)
    private String formasTrabajo;
    @Lob
    @Column(nullable = true)
    private String miniGaleria;
    @Lob
    @Column(nullable = true)
    private String adicionalInfo;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<Servicio> servicios;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<CuentaPago> cuentas;

    @Column(nullable = true)
    private UUID uuid;

    @Column(nullable = true)
    private String rutaWebImg;

    @Column(nullable = true)
    private String rutaRealImg;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TrainerId", referencedColumnName = "SecurityUserId", updatable = false)
    private Trainer trainer;

    public TrainerFicha() {
        // TODO Auto-generated constructor stub
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public void setTrainer(Integer trainerId){
        this.trainer = new Trainer(trainerId);
    }

}
