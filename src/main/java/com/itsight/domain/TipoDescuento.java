package com.itsight.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "tipoDescuento", attributeNodes = {}),
})
@Data
public class TipoDescuento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TipoDescuentoId")
    private int id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private boolean flagActivo;

    public TipoDescuento() {
    }

    public TipoDescuento(int id) {
        this.id = id;
    }

    public TipoDescuento(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public TipoDescuento(String nombre) {
        this.nombre = nombre;
    }
}
