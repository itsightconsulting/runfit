package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itsight.domain.base.AuditingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Correo extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CorreoId")
    private Integer id;

    @Column(nullable = false)
    private String asunto;

    @Lob
    @Column(nullable = false)
    private String body;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ModuloId")
    private Modulo modulo;

    public Correo(){}

    public Correo(String asunto, String body, Integer moduloId) {
        this.asunto = asunto;
        this.body = body;
        this.modulo = new Modulo(moduloId);
    }
}
