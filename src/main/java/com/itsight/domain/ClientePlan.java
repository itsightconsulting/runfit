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
        @NamedEntityGraph(name = "usuarioPlan"),
        @NamedEntityGraph(name = "usuarioPlan.all", attributeNodes = {
                @NamedAttributeNode(value = "cliente"),
                @NamedAttributeNode(value = "plan")}),
})
@Data
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@EqualsAndHashCode(callSuper = false)
public class ClientePlan extends AuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UsuarioPlanId")
    private int id;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClienteId", referencedColumnName = "SecurityUserId", updatable = false)
    private Cliente cliente;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PlanId", updatable = false)
    private Plan plan;

    @Column
    private int Mes;

    public ClientePlan(int id, Cliente cliente, Plan plan, int mes) {
        this.id = id;
        this.cliente = cliente;
        this.plan = plan;
        Mes = mes;
    }

    public ClientePlan() { }
}
