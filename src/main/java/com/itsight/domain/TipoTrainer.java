package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class TipoTrainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TipoTrainerId")
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    /*@JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoTrainer")
    private List<Trainer> lstCliente;*/
}
