package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlanId")
    private int id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private boolean flagActivo;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "plan")
    private List<Paquete> lstPaquete;

    public Plan() {
    }

    public Plan(int id) {
        this.id = id;
    }

    public Plan(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Plan(String nombre, String descripcion, boolean flagActivo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.flagActivo = flagActivo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return "Plan [id=" + id + ", nombre=" + nombre + ", flagActivo=" + flagActivo + "]";
    }

}
