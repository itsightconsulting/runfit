package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ModuloId")
    private Integer id;

    @Column(unique = true)
    private String nombre;

    private String descripcion;

    @JsonBackReference
    @OneToMany(mappedBy = "modulo")
    private List<Correo> correos;

    public Modulo(){}

    public Modulo(Integer id){
        this.id = id;
    }

    public Modulo(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}
