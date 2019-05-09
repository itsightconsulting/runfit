package com.itsight.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class ContactoTrainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContactoTrainerId")
    private Integer id;
    @Column(name = "Nombre", nullable = false)
    private String nombre;
    @Column(name = "Sexo", nullable = false)
    private Integer sexo;
    @Column(name = "Correo", nullable = false)
    private String correo;
    @Column(name = "Movil", nullable = false)
    private String movil;
    @Column(name = "mensaje", nullable = false)
    private String mensaje;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FechaCreacion", nullable = false, updatable = false)
    private Date fechaCreacion;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FechaExpiracion", nullable = false, updatable = false)
    private Date fechaExpiracion;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FechaVisualizacion")
    private Date fechaVisualizacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TrainerId", referencedColumnName = "SecurityUserId", updatable = false)
    private Trainer trainer;
    @Column(name = "FlagLeido")
    private boolean flagLeido;
    @Column(name = "FlagLeidoFueraFecha")
    private boolean flagLeidoFueraFecha;

    @Transient
    private String correoTrainer;

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public void setTrainer(Integer trainerId) {
        this.trainer = new Trainer(trainerId);
    }
}
