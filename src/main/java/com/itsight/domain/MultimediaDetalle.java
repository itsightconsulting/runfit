package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itsight.domain.base.AuditingEntity;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "multimedia_detalle"),
        @NamedEntityGraph(name = "multimedia_detalle.all", attributeNodes = {
                @NamedAttributeNode(value = "usuario"),
        })
})
@Data
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@EqualsAndHashCode(callSuper = false)
public class MultimediaDetalle extends AuditingEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "multimedia_detalle_seq")
    @GenericGenerator(
            name = "multimedia_detalle_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @org.hibernate.annotations.Parameter(name = "multimedia_detalle_seq", value = "multimedia_detalle_seq"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )

    @Column(name = "MultimediaDetalleId")
    private int id;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MultimediaEntrenadorId", updatable = false)
    private MultimediaEntrenador multimediaentrenador;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UsuarioId", updatable = false)
    private Usuario usuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MultimediaEntrenador getMultimediaentrenador() {
        return multimediaentrenador;
    }

    public void setMultimediaentrenador(int multimediaentrenador) {
        this.multimediaentrenador = new MultimediaEntrenador(multimediaentrenador);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = new Usuario(usuario);
    }

    public MultimediaDetalle(int id) {
        this.id = id;
    }

    public MultimediaDetalle() {

    }
}
