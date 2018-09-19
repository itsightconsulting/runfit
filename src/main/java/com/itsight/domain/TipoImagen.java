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
    private int id;
    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, precision = 5, scale = 1)
    private BigDecimal ancho;

    @Column(nullable = false, precision = 5, scale = 1)
    private BigDecimal alto;

    public TipoImagen() {
        // TODO Auto-generated constructor stub
    }

    public TipoImagen(int id) {
        this.id = id;
    }

    public TipoImagen(String nombre, Double ancho, Double alto) {
        this.nombre = nombre;
        this.ancho = BigDecimal.valueOf(ancho);
        this.alto = BigDecimal.valueOf(alto);
    }

}
