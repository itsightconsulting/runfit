package com.itsight.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DisciplinaId")
    private Integer id;

    @Size(min = 1, max = 255)
    @NaturalId
    private String nombre;

    @ManyToMany(mappedBy = "disciplinas")
    private Set<Trainer> trainers = new HashSet<>();

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

    public Set<Trainer> getTrainers() {
        return trainers;
    }

    public void setTrainers(Set<Trainer> trainers) {
        this.trainers = trainers;
    }
}
