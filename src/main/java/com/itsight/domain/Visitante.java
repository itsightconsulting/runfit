package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
public class Visitante {

    @GeneratedValue(generator = "visitante_seq")
    @GenericGenerator(
            name = "visitante_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @org.hibernate.annotations.Parameter(name = "visitante_seq", value = "visitante_seq"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @Column(name = "VisitanteId")
    private Integer id;

    @Column(name = "Nombres", nullable = false)
    private String nombres;

    @Column(name = "Apellidos", nullable = false)
    private String apellidos;

    @Column(name = "Correo", updatable = false)
    private String correo;

    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "SecurityUserId")
    private SecurityUser securityUser;


}
