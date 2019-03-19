package com.itsight.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonMoneySimpleSerializer;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedEntityGraphs({
    @NamedEntityGraph(name = "kilometrajeBase")
})
@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Data
public class KilometrajeBase implements Serializable {

    @Id
    @GeneratedValue(generator = "kilo_base_seq")
    @GenericGenerator(
            name = "kilo_base_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @org.hibernate.annotations.Parameter(name = "kilo_base_seq", value = "kilo_base_seq"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            })
    @Column(name="KilometrajeBaseId")
    private Integer id;
    @Column(updatable = false)
    private int distancia;
    @Column(updatable = false)
    private int nivel;
    @Column(updatable = false)
    private int etapa;
    @Column(precision = 5, scale = 2)
    private double kilometraje;

    public KilometrajeBase() {}

    public KilometrajeBase(int distancia, int nivel, int etapa, double kilometraje) {
        this.distancia = distancia;
        this.nivel = nivel;
        this.etapa = etapa;
        this.kilometraje = kilometraje;
    }
}
