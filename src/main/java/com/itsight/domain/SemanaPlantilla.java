package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonDateSimpleDeserializer;
import com.itsight.json.JsonDateSimpleSerializer;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "semanaPlantilla")
})
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Data
@Entity
public class SemanaPlantilla {

    @Id
    @GeneratedValue(generator = "semana_plantilla_seq")
    @GenericGenerator(
            name = "semana_plantilla_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @Parameter(name = "semana_plantilla_seq", value = "semana_plantilla_seq"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "SemanaPlantillaId")
    private int id;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @Column
    private boolean flagFull;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RutinaPlantillaId")
    private RutinaPlantilla rutinaPlantilla;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "semanaPlantilla", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaPlantilla> lstDiaPlantilla;

    public SemanaPlantilla(){}

}
