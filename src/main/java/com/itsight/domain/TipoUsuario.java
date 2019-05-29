package com.itsight.domain;

import javax.persistence.*;

@Entity
public class TipoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TipoUsuarioId")
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    public TipoUsuario() {
    }

    public TipoUsuario(int id) {
        this.id = id;
    }

    public TipoUsuario(String nombre) {
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
