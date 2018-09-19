package com.itsight.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Musculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MusculoId")
    private int id;

    @Column
    private String nombre;

    public Musculo(){}

    public Musculo(String nombre) {
        this.nombre = nombre;
    }
}
