package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
public class AudioTrainer {


    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer categoriaAudioId;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = true)
    private String rutaWeb;

    @Column(nullable = true)
    private String duracion;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(nullable = false)
    private UUID uuid;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoAudioId")
    private TipoAudio tipoAudio;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TrainerId", referencedColumnName = "SecurityUserId")
    private Trainer trainer;

}
