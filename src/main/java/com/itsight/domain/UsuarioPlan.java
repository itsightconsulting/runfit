package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itsight.domain.base.AuditingEntity;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "usuarioplan"),
        @NamedEntityGraph(name = "usuarioplan.all", attributeNodes = {
                @NamedAttributeNode(value = "usuario"),
                @NamedAttributeNode(value = "plan")}),
})
@Data
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@EqualsAndHashCode(callSuper = false)
public class UsuarioPlan extends AuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UsuarioPlanId")
    private int id;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UsuarioId")
    private Usuario usuario;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PlanId", updatable = false)
    private Plan plan;

    @Column
    private int Mes;

    public UsuarioPlan(int id, Usuario usuario, Plan plan, int mes) {
        this.id = id;
        this.usuario = usuario;
        this.plan = plan;
        Mes = mes;
    }

    public UsuarioPlan() { }
}
