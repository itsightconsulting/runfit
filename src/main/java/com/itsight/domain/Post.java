package com.itsight.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.itsight.domain.base.AuditingEntity;
import com.itsight.domain.jsonb.PostDetalle;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@TypeDefs({
    @TypeDef(name="jsonb", typeClass = JsonBinaryType.class)
})
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Post extends AuditingEntity implements Serializable {

    @Id
    private Integer id;

    @Column(nullable = false)
    private int tipo;

    @Column
    private String titulo;

    @Column
    private String descripcion;

    @Column(updatable = false)
    private UUID uuid;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column
    private String peso;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column
    private String duracion;

    @Column()
    private String rutaWeb;

    @Type(type = "jsonb")
    @Column(name = "Detalle", columnDefinition = "jsonb")
    private List<PostDetalle> postDetalle;
}
