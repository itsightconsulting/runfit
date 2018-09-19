package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlanId")
    private int id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column
    private double precioSoles;

    @Column
    private double descuentoSoles;

    @Column
    private double precioDolares;

    @Column
    private double descuentoDolares;

    @Column
    private int cantidadMeses;


    @Column(nullable = false)
    private boolean flagActivo;


    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "plan")
    private List<Paquete> lstPaquete;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "plan")
    private List<UsuarioPlan> lstUsurioPlan;

    public Plan() {
    }

    public Plan(int id) {
        this.id = id;
    }

    public Plan(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Plan(double preciosoles, double descuentosoles, double preciodolares, double descuentodolares, int cantidadmeses) {
        this.precioSoles = preciosoles;
        this.descuentoSoles = descuentosoles;
        this.precioDolares = preciodolares;
        this.descuentoDolares = descuentodolares;
        this.cantidadMeses = cantidadmeses;
    }


    public Plan(String nombre, String descripcion, boolean flagActivo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.flagActivo = flagActivo;
    }

}
