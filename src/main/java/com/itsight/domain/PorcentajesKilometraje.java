package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.itsight.domain.jsonb.PorcKiloTipo;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "porcentajesKilometraje")
})
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Data
public class PorcentajesKilometraje implements Serializable {

    @Id
    @GeneratedValue(generator = "porcentajes_kilometraje_seq")
    @GenericGenerator(
            name = "porcentajes_kilometraje_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @org.hibernate.annotations.Parameter(name = "porcentajes_kilometraje_seq", value = "porcentajes_kilometraje_seq"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            })
    @Column(name="PorcentajesKilometrajeId")
    private Integer id;

    @Column(updatable = false)
    private int distancia;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<PorcKiloTipo> porcKiloTipos;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TrainerId", referencedColumnName = "SecurityUserId", updatable = false)
    private Trainer trainer;

}
