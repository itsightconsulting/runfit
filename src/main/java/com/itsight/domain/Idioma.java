package com.itsight.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Idioma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdiomaId")
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String iso;

    public Idioma(String nombre, String iso) {
        this.nombre = nombre;
        this.iso = iso;
    }

    public Idioma() {
    }
}
