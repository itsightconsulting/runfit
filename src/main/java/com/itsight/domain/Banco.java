package com.itsight.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Banco {

    @Id
    @Column(name = "BancoId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    public Banco() {
    }

    public Banco(String nombre) {
        this.nombre = nombre;
    }

    public Banco(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
