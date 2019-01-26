package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itsight.domain.base.AuditingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NamedEntityGraphs({
    @NamedEntityGraph(name = "confGen"),
    @NamedEntityGraph(name = "confGen.tipoUsuario", attributeNodes = {
        @NamedAttributeNode(value = "tipoUsuario")
    })
})
public class ConfiguracionGeneral extends AuditingEntity {

    @Id
    @Column(name = "ConfiguracionGeneralId")
    @GeneratedValue(generator = "conf_gen_seq")
    @GenericGenerator(
            name = "conf_gen_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @org.hibernate.annotations.Parameter(name = "conf_gen_seq", value = "conf_gen_seq"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private int id;

    @NotNull
    @Size(min=4)
    @Column(nullable = false, updatable = false)
    private String nombre;

    @NotNull
    @Size(min=1)
    @Column(nullable = false)
    private String valor;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoUsuarioId")
    private TipoUsuario tipoUsuario;

    public ConfiguracionGeneral(){}

    public ConfiguracionGeneral(String nombre, String valor, boolean flagActivo, int tipoUsuarioId) {
        this.nombre = nombre;
        this.valor = valor;
        this.tipoUsuario = new TipoUsuario(tipoUsuarioId);
        this.setFlagActivo(flagActivo);
    }
}
