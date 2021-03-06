package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.jsonb.Elemento;
import com.itsight.json.JsonDateSimpleDeserializer;
import com.itsight.json.JsonDateSimpleSerializer;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "dia"),
})
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Data
@Entity
public class Dia {

    @Id
    @GeneratedValue(generator = "dia_seq")
    @GenericGenerator(
            name = "dia_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @Parameter(name = "dia_seq", value = "dia_seq"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "DiaId")
    private Integer id;
    @Column
    private int dia;
    @Column
    private String literal;
    @Column
    private String smsHeader;
    @Column
    private String nota;
    @Column
    private String voz;
    @Column
    private Boolean flagDescanso;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column
    private int minutos;

    @Column
    private double distancia;

    @Column
    private double calorias;

    @Column
    private Integer  avanceDiario;

    @Column
    private Integer esfuerzoDiario;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Elemento> elementos;

    @JsonBackReference
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(/*fetch = FetchType.LAZY*/)
    @JoinColumn(name = "SemanaId")
    private Semana semana;

    @Column
    private Boolean flagEnvioCliente;

    public Dia(){}

    public Dia(DiaPlantilla diaPlantilla){
       this.calorias = diaPlantilla.getCalorias();
       this.dia = diaPlantilla.getDia();
       this.distancia = diaPlantilla.getDistancia();
       this.elementos = diaPlantilla.getElementos();
       this.fecha = diaPlantilla.getFecha();
       this.flagDescanso = diaPlantilla.getFlagDescanso();
       this.flagEnvioCliente = diaPlantilla.getFlagEnvioCliente();
       this.literal = diaPlantilla.getLiteral();
       this.minutos = diaPlantilla.getMinutos();
       this.nota = diaPlantilla.getNota();
       this.smsHeader = diaPlantilla.getSmsHeader();
       this.voz = diaPlantilla.getVoz();
    }

    public String getDiaLiteral(){
        return this.literal + " " + this.dia;
    }
}
