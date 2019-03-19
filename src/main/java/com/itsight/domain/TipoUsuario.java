package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class TipoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TipoUsuarioId")
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoUsuario")
    private List<Cliente> lstCliente;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoUsuario")
    private List<ConfiguracionGeneral> lstConfGeneral;

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

    public List<Cliente> getLstCliente() {
        return lstCliente;
    }

    public void setLstCliente(List<Cliente> lstCliente) {
        this.lstCliente = lstCliente;
    }

    public List<ConfiguracionGeneral> getLstConfGeneral() {
        return lstConfGeneral;
    }

    public void setLstConfGeneral(List<ConfiguracionGeneral> lstConfGeneral) {
        this.lstConfGeneral = lstConfGeneral;
    }
}
