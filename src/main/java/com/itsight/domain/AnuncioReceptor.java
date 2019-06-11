package com.itsight.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ClienteId","AnuncioTrainerId"}),

})
public class AnuncioReceptor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AnuncioReceptorId")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AnuncioTrainerId")
    private AnuncioTrainer anuncioTrainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClienteId", referencedColumnName = "SecurityUserId")
    private Cliente cliente;

    @Column(nullable = false)
    private boolean flagLeido;

}
