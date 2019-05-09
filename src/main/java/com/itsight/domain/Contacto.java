package com.itsight.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContactoId")
    private Integer id;
    @Column(name = "Nombre", nullable = false)
    private String nombre;
    @Column(name = "Correo", nullable = false)
    private String correo;
    @Column(name = "Movil", nullable = false)
    private String movil;
    @Column(name = "mensaje", nullable = false)
    private String mensaje;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FechaCreacion", nullable = false, updatable = false)
    private Date fechaCreacion;

}
