package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.jsonb.*;
import com.itsight.domain.jsonb.Objetivo;
import com.itsight.json.JsonMoneySimpleSerializer;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@NamedEntityGraphs({
    @NamedEntityGraph(name = "clienteFitness.cliente",
        attributeNodes = {
            @NamedAttributeNode(value = "cliente")
    }),
    @NamedEntityGraph(name = "clienteFitness")
})
@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Entity
@Data
public class ClienteFitness implements Serializable {

    @Id
    @GeneratedValue(generator = "cliente_fitness_seq")
    @GenericGenerator(
        name = "cliente_fitness_seq",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "prefer_sequence_per_entity", value = "true"),
                @Parameter(name = "cliente_fitness_seq", value = "cliente_fitness_seq"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1")
        })
    @Column(name="ClienteFitnessId")
    private int id;
    @Column
    private String correoSecundario;
    @Column
    private int estadoCivil;
    @Column(nullable = false)
    private int sexo;
    @JsonSerialize(using = JsonMoneySimpleSerializer.class)
    @Column(precision = 5, scale = 2, nullable = false)
    private BigDecimal peso;
    @Column(nullable = false)
    private int talla;
    @Column(nullable = false)
    private int imc;
    @Column(nullable = false)
    private int nivel;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<CondicionMejora> mejoras;
    @Column
    private String tiempoUnKilometro;
    @JsonSerialize(using = JsonMoneySimpleSerializer.class)
    @Column(precision = 4, scale = 2, nullable = false)
    private BigDecimal kilometrajePromedioSemana;
    @Column(nullable = false)
    private int diasSemanaCorriendo;
    @Column(nullable = false)
    private boolean flagHaceCalentamiento;
    @Column(nullable = false)
    private boolean flagHaceEstiramientos;
    @Column(nullable = false)
    private String desgasteZapatilla;
    @Column(nullable = false)
    private String objetivosDescripcion;
    @Column(nullable = false)
    private String terrenoPredominante;
    @Column(nullable = false)
    private int diaDescanso;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<TiempoDisponible> tiemposDisponibles;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<Objetivo> objetivos;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private CondicionAnatomica condicionAnatomica;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<CompetenciaRunner> competencias;
    @Column(nullable = false)
    private int frecuenciaComunicacion;
    @Column(nullable = false)
    private String viaConexion;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClienteId", referencedColumnName = "SecurityUserId", updatable = false)
    private Cliente cliente;

    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }

    public void setCliente(int usuarioId){
        this.cliente = new Cliente(usuarioId);
    }

}