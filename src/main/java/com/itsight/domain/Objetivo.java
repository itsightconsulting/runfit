package com.itsight.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Objetivo {

    @Id
    @GeneratedValue(generator = "objetivo_seq")
    @GenericGenerator(
            name = "objetivo_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @Parameter(name = "objetivo_seq", value = "objetivo_seq"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "ObjetivoId")
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private boolean flagActivo;

    public Objetivo() {
    }


    public Objetivo(String nombre, String descripcion, boolean flagActivo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.flagActivo = flagActivo;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isFlagActivo() {
        return flagActivo;
    }

    public void setFlagActivo(boolean flagActivo) {
        this.flagActivo = flagActivo;
    }


    @Override
    public String toString() {
        return "Objetivo [id=" + id + ", nombre=" + nombre + ", flagActivo=" + flagActivo + "]";
    }

}
