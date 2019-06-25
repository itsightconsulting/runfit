package com.itsight.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class UsuarioRecover {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String schema;

    @Column(nullable = false)
    private boolean flagRecover;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLimite;

    public UsuarioRecover() {
    }

    public UsuarioRecover(Integer id, String schema, boolean flagRecover, Date fechaLimite) {
        this.id = id;
        this.schema = schema;
        this.flagRecover = flagRecover;
        this.fechaLimite = fechaLimite;
    }
}
