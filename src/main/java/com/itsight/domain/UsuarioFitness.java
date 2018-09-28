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

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@NamedEntityGraphs({
    @NamedEntityGraph(name = "usuarioFitness.usuario",
        attributeNodes = {
            @NamedAttributeNode(value = "usuario")
    }),
    @NamedEntityGraph(name = "usuarioFitness")
})
@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Entity
@Data
public class UsuarioFitness implements Serializable {

    @Id
    @GeneratedValue(generator = "usuario_fitness_seq")
    @GenericGenerator(
        name = "usuario_fitness_seq",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "prefer_sequence_per_entity", value = "true"),
                @Parameter(name = "usuario_fitness_seq", value = "usuario_fitness_seq"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1")
        })
    @Column(name="UsuarioFitnessId")
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
    @JoinColumn(name = "UsuarioId", updatable = false)
    private Usuario usuario;

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    public void setUsuario(int usuarioId){
        this.usuario = new Usuario(usuarioId);
    }

}
