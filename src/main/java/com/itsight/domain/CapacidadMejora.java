package com.itsight.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CapacidadMejora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CapacidadMejoraId")
    private int id;

    @Column
    private String nombre;

    public CapacidadMejora(){}

    public CapacidadMejora(String nombre) {
        this.nombre = nombre;
    }
}
