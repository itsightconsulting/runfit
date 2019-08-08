package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonDateSimpleDeserializer;
import com.itsight.json.JsonDateSimpleSerializer;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
    private Boolean flagFull;

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


    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RutinaPlantillaId")
    private RutinaPlantilla rutinaPlantilla;

    @JsonBackReference
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(/*fetch = FetchType.LAZY,*/ mappedBy = "semanaPlantilla", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaPlantilla> lstDiaPlantilla;

    public SemanaPlantilla(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean isFlagFull() {
        return flagFull;
    }

    public void setFlagFull(boolean flagFull) {
        this.flagFull = flagFull;
    }

    public double getKilometrajeTotal() {
        return kilometrajeTotal;
    }

    public void setKilometrajeTotal(double kilometrajeTotal) {
        this.kilometrajeTotal = kilometrajeTotal;
    }

    public double getKilometrajeActual() {
        return kilometrajeActual;
    }

    public void setKilometrajeActual(double kilometrajeActual) {
        this.kilometrajeActual = kilometrajeActual;
    }

    public double getCalorias() {
        return calorias;
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }

    public double getHoras() {
        return horas;
    }

    public void setHoras(double horas) {
        this.horas = horas;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    public String getMetricas() {
        return metricas;
    }

    public void setMetricas(String metricas) {
        this.metricas = metricas;
    }

    public String getMetricasVelocidad() {
        return metricasVelocidad;
    }

    public void setMetricasVelocidad(String metricasVelocidad) {
        this.metricasVelocidad = metricasVelocidad;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public Boolean isFlagEnvioCliente() {
        return flagEnvioCliente;
    }

    public void setFlagEnvioCliente(boolean flagEnvioCliente) {
        this.flagEnvioCliente = flagEnvioCliente;
    }

    public RutinaPlantilla getRutinaPlantilla() {
        return rutinaPlantilla;
    }

    public void setRutinaPlantilla(RutinaPlantilla rutinaPlantilla) {
        this.rutinaPlantilla = rutinaPlantilla;
    }

    public List<DiaPlantilla> getLstDiaPlantilla() {
        return lstDiaPlantilla;
    }

    public void setLstDiaPlantilla(List<DiaPlantilla> lstDiaPlantilla) {
        this.lstDiaPlantilla = lstDiaPlantilla;
    }
}
