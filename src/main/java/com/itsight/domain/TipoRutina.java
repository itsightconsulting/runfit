package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity

public class TipoRutina {

    @Id
    @GeneratedValue(generator = "tipo_rutina_seq")
    @GenericGenerator(
            name = "tipo_rutina_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @org.hibernate.annotations.Parameter(name = "tipo_rutina_seq", value = "tipo_rutina_seq"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name="TipoRutinaId")
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoRutina")
    private List<Rutina> lstRutina;

    //@Column(nullable = false)
    private Boolean flagActivo;



    public TipoRutina() {
    }


    public TipoRutina(Integer id) {
        this.id = id;
    }

    public TipoRutina(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public TipoRutina(Integer id, String nombre, List<Rutina> lstRutina, Boolean flagActivo) {
        this.id = id;
        this.nombre = nombre;
        this.lstRutina = lstRutina;
        this.flagActivo = flagActivo;
    }

    public TipoRutina(Integer id, String nombre, List<Rutina> lstRutina) {
        this.id = id;
        this.nombre = nombre;
        this.lstRutina = lstRutina;
    }

    public Boolean isFlagActivo() {
        return flagActivo;
    }

    public void setFlagActivo(Boolean flagActivo) {
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

    public List<Rutina> getLstRutina() {
        return lstRutina;
    }

    public void setLstRutina(List<Rutina> lstRutina) {
        this.lstRutina = lstRutina;
    }

    public TipoRutina(String nombre) {
        this.nombre = nombre;
    }
}
