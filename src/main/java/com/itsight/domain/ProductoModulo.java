package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ProductoModulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductoModuloId")
    private int id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String capitulos;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductoId")
    private Producto producto;
}
