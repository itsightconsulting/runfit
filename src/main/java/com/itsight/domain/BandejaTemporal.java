package com.itsight.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class BandejaTemporal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String asunto;
    @Column(columnDefinition = "TEXT")
    private String contenido;
    private String url;

    public BandejaTemporal(){}

    public BandejaTemporal(String asunto, String contenido, String url) {
        this.asunto = asunto;
        this.contenido = contenido;
        this.url = url;
    }
}
