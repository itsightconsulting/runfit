package com.itsight.domain.base;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonDateSimpleDeserializer;
import com.itsight.json.JsonDateSimpleSerializer;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@MappedSuperclass
@Data
public class AuditingEntity {

    @Column(nullable = false, updatable = false)
    private String creador;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date fechaCreacion;

    @Column(nullable = true)
    private String modificadoPor;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date fechaModificacion;

    @Column(nullable = false)
    private boolean flagActivo;

    @Column(nullable = false)
    private boolean flagEliminado;

    @Column(nullable = true)
    private Long rowVersion;

}
