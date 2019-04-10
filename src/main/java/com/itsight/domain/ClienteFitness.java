package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.jsonb.CondicionMejora;
import com.itsight.domain.jsonb.*;
import com.itsight.json.JsonMoneySimpleSerializer;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.*;
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
    private Integer id;
    @Column
    private String correoSecundario;
    @Column
    private Integer estadoCivil;
    @Column(nullable = false)
    private Integer sexo;
    @JsonSerialize(using = JsonMoneySimpleSerializer.class)
    @Column(precision = 5, scale = 2, nullable = false)
    private BigDecimal peso;
    @Column(nullable = false)
    private Integer talla;
    @Column(nullable = false)
    private Integer imc;
    @Column(nullable = false)
    private Integer nivel;//Nivel del atleta
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<CondicionMejora> mejoras;
    @Column
    private String tiempoUnKilometro;
    @JsonSerialize(using = JsonMoneySimpleSerializer.class)
    @Column(precision = 4, scale = 2, nullable = false)
    private BigDecimal kilometrajePromedioSemana;
    @Column(nullable = false)
    private Integer diasSemanaCorriendo;
    @Column(nullable = false)
    private boolean flagCalentamiento;
    @Column(nullable = false)
    private boolean flagEstiramientos;
    @Column(nullable = false)
    private String desgasteZapatilla;
    @Column(nullable = false)
    private String desObjetivos;
    @Column(nullable = false)
    private String desTerPredom;
    //@Column(nullable = false)
    //private int diaDescanso;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<TiempoDisponible> tiemposDisponibles;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private CondicionAnatomica condicionAnatomica;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private Salud salud;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<CompetenciaRunner> competencias;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<FitElemento> fitElementos;
    @Column(nullable = false)
    private Integer frecuenciaComunicacion;
    @Column(nullable = false)
    private Integer viaConexion;
    @Column(nullable = true)
    private String contEmergencia;
    @Column(nullable = false)
    private String tiempoDistancia;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClienteId", referencedColumnName = "SecurityUserId", updatable = false)
    private Cliente cliente;

    public ClienteFitness(){}

    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }

    public void setCliente(Integer cliId){
        this.cliente = new Cliente(cliId);
    }

}
