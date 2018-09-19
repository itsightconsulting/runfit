package com.itsight.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RolId")
    private int id;

    @Column
    private String nombre;

    @Column
    private String rol;

    public Rol(){}

    public Rol(String nombre, String rol) {
        this.nombre = nombre;
        this.rol = rol;
    }
}
