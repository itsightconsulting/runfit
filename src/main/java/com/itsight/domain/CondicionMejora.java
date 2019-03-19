package com.itsight.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CondicionMejora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CondicionMejoraId")
    private Integer id;

    @Column
    private String nombre;

    public CondicionMejora(){}

    public CondicionMejora(String nombre) {
        this.nombre = nombre;
    }
}
