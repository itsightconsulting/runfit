package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.dto.UsuGenDTO;
import com.itsight.json.JsonDateSimpleDeserializer;
import com.itsight.json.JsonDateSimpleSerializer;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.*;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "semana"),
        @NamedEntityGraph(name = "semana.all",
            attributeNodes={
                @NamedAttributeNode(value = "lstDia"),
                //@NamedAttributeNode(value = "rutina"),
            })
})
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Data
@Entity
public class Semana {

    @Id
    @GeneratedValue(generator = "semana_seq")
    @GenericGenerator(
            name = "semana_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @Parameter(name = "semana_seq", value = "semana_seq"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "SemanaId")
    private Integer id;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column
    private Boolean flagFull;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RutinaId")
    private Rutina rutina;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "semana", cascade = CascadeType.ALL)
    private List<Dia> lstDia;

    @Column(nullable = false)
    private double kilometrajeTotal;

    @Column(nullable = false)
    private double kilometrajeActual;

    @Column(nullable = false)
    private double calorias;

    @Column(nullable = false)
    private double horas;

    @Column
    private String objetivos;

    @Column(columnDefinition="TEXT")
    private String metricas;

    @Column(columnDefinition = "text")
    private String metricasVelocidad;

    @Column(nullable = true)
    private String prioridad;

    @Column
    private Boolean flagEnvioCliente;

    public Semana(){}

    public Semana(SemanaPlantilla semanaPlantilla) {
        this.calorias = semanaPlantilla.getCalorias();
        this.fechaFin = semanaPlantilla.getFechaFin();
        this.fechaInicio = semanaPlantilla.getFechaInicio();
        this.flagEnvioCliente = semanaPlantilla.getFlagEnvioCliente();
        this.flagFull = semanaPlantilla.getFlagFull();
        this.horas = semanaPlantilla.getHoras();
        this.kilometrajeActual = semanaPlantilla.getKilometrajeActual();
        this.kilometrajeTotal = semanaPlantilla.getKilometrajeTotal();
        this.metricas = semanaPlantilla.getMetricas();
        this.metricasVelocidad = semanaPlantilla.getMetricasVelocidad();
        this.objetivos = semanaPlantilla.getObjetivos();
        this.prioridad = semanaPlantilla.getPrioridad();
    }

    public void setRutina(Rutina rutina) {
        this.rutina = rutina;
    }

    public void setRutina(Integer rutinaId) {
        this.rutina = new Rutina(rutinaId);
    }

}
