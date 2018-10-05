package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itsight.domain.jsonb.Elemento;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiaRutinario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiaRutinarioId")
    private int id;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Column
    private int minutos;

    @Column
    private double distancia;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Elemento> elementos;

    public void setFechaCreacion(){
        this.fechaCreacion = new Date();
    }

}
