package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "paquete.plan",
                attributeNodes = {
                        @NamedAttributeNode(value = "plan")
                }),
        @NamedEntityGraph(name = "paquete",
                attributeNodes = {
                })
})
public class Paquete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaqueteId")
    private int id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal precio;

    @Column(nullable = true, precision = 8, scale = 2)
    private BigDecimal ahorro;

    @Column(nullable = false)
    private String nombreContrato;

    @Column(nullable = false)
    private String rutaContrato;

    @Column(nullable = false)
    private boolean flagActivo;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "PlanId")
    private Plan plan;

    @Transient
    private int fkPlan;

    public Paquete() {
    }

    public Paquete(String nombreContrato, String rutaContrato) {
        this.nombreContrato = nombreContrato;
        this.rutaContrato = rutaContrato;
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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getAhorro() {
        return ahorro;
    }

    public void setAhorro(BigDecimal ahorro) {
        this.ahorro = ahorro;
    }

    public String getRutaContrato() {
        return rutaContrato;
    }

    public void setRutaContrato(String rutaContrato) {
        this.rutaContrato = rutaContrato;
    }

    public String getNombreContrato() {
        return nombreContrato;
    }

    public void setNombreContrato(String nombreContrato) {
        this.nombreContrato = nombreContrato;
    }

    public boolean isFlagActivo() {
        return flagActivo;
    }

    public void setFlagActivo(boolean flagActivo) {
        this.flagActivo = flagActivo;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public void setPlan(int planId) {
        this.plan = new Plan(planId);
    }

    public int getFkPlan() {
        return fkPlan;
    }

    public void setFkPlan(int fkPlan) {
        this.fkPlan = fkPlan;
    }

    @Override
    public String toString() {
        return "Paquete [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio
                + ", ahorro=" + ahorro + ", rutaContrato=" + rutaContrato + ", nombreContrato=" + nombreContrato
                + ", flagActivo=" + flagActivo + ", fkPlan=" + fkPlan + "]";
    }


}
