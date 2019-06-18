package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class TipoRutina {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TipoRutinaId")
    private Integer id;


    @NaturalId
    @Column(nullable = false)
    private String nombre;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoRutina")
    private List<Rutina> lstRutina;


    public TipoRutina() {
    }


    public TipoRutina(Integer id) {
        this.id = id;
    }

    public TipoRutina(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public TipoRutina(Integer id, String nombre, List<Rutina> lstRutina) {
        this.id = id;
        this.nombre = nombre;
        this.lstRutina = lstRutina;
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

    public List<Rutina> getLstRutina() {
        return lstRutina;
    }

    public void setLstRutina(List<Rutina> lstRutina) {
        this.lstRutina = lstRutina;
    }
}
