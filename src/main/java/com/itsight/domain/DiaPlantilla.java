package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.jsonb.ListaPlantilla;
import com.itsight.json.JsonDateSimpleDeserializer;
import com.itsight.json.JsonDateSimpleSerializer;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "diaPlantilla"),
})
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Data
@Entity
public class DiaPlantilla {

    @Id
    @GeneratedValue(generator = "dia_plantilla_seq")
    @GenericGenerator(
            name = "dia_plantilla_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @Parameter(name = "dia_plantilla_seq", value = "dia_plantilla_seq"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "DiaPlantillaId")
    private Long id;
    @Column
    private int dia;
    @Column
    private String literal;
    @Column
    private boolean flagDescanso;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<ListaPlantilla> listas;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SemanaPlantillaId")
    private SemanaPlantilla semanaPlantilla;

    public DiaPlantilla(){}

    public String getDiaLiteral(){
        return this.literal + " " + this.dia;
    }
}
