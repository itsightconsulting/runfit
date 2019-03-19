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
public class MiMiniRutina implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MiMiniRutinaId")
    private Long id;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Column
    private int minutos;

    @Column
    private double distancia;

    @Column(nullable = false)
    private int nivel;

    @Column(nullable = false)
    private String metrica;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Elemento> elementos;

    public void setFechaCreacion(){
        this.fechaCreacion = new Date();
    }

}
