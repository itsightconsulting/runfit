package com.itsight.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DisciplinaId")
    private Integer id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false)
    private String nombre;

    public Disciplina() {}

    public Disciplina(Integer id) {
        this.id = id;
    }

    public Disciplina(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



}
