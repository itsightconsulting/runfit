package com.itsight.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class TipoImagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TipoImagenId")
    private Integer id;
    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, precision = 5, scale = 1)
    private Double ancho;

    @Column(nullable = false, precision = 5, scale = 1)
    private Double alto;

    public TipoImagen() {
        // TODO Auto-generated constructor stub
    }

    public TipoImagen(Integer id) {
        this.id = id;
    }

    public TipoImagen(String nombre, Double ancho, Double alto) {
        this.nombre = nombre;
        this.ancho = ancho;
        this.alto = alto;
    }

}
