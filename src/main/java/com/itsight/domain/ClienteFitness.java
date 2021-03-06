package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.jsonb.CompetenciaRunner;
import com.itsight.domain.jsonb.CondicionAnatomica;
import com.itsight.domain.jsonb.CondicionMejora;
import com.itsight.domain.jsonb.Salud;
import com.itsight.domain.pojo.ClienteFitnessPOJO;
import com.itsight.json.JsonMoneyDoubleSimpleSerializer;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.*;
import java.io.Serializable;
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

@SqlResultSetMappings({
        @SqlResultSetMapping(name = "resultMappingClienteFitness",
                classes = {
                        @ConstructorResult(
                                targetClass = ClienteFitnessPOJO.class,
                                columns = {
                                        @ColumnResult(name = "id"),
                                        @ColumnResult(name = "competencias"),
                                        @ColumnResult(name = "condicionAnatomica"),
                                        @ColumnResult(name = "desObjetivos"),
                                        @ColumnResult(name = "desTerPredom"),
                                        @ColumnResult(name = "desTerPredomOtro"),
                                        @ColumnResult(name = "desgasteZapatilla"),
                                        @ColumnResult(name = "desgasteZapatillaOtro"),
                                        @ColumnResult(name = "diasSemanaCorriendo"),
                                        @ColumnResult(name = "estadoCivil"),
                                        @ColumnResult(name = "fitElementos"),
                                        @ColumnResult(name = "flagCalentamiento"),
                                        @ColumnResult(name = "flagEstiramiento"),
                                        @ColumnResult(name = "frecuenciaComunicacion"),
                                        @ColumnResult(name = "imc"),
                                        @ColumnResult(name = "kilometrajePromedioSemana"),
                                        @ColumnResult(name = "mejoras"),
                                        @ColumnResult(name = "nivel"),
                                        @ColumnResult(name = "peso"),
                                        @ColumnResult(name = "salud"),
                                        @ColumnResult(name = "sexo"),
                                        @ColumnResult(name = "talla"),
                                        @ColumnResult(name = "tiempoDistancia"),
                                        @ColumnResult(name = "tiempoUnKilometro"),
                                        @ColumnResult(name = "tipoCanalVentaId"),
                                        @ColumnResult(name = "fechaCreacion"),
                                        @ColumnResult(name = "fechaModificacion"),
                                        @ColumnResult(name = "flagActivo"),
                                        @ColumnResult(name = "correo"),
                                        @ColumnResult(name = "fechaNacimiento"),
                                        @ColumnResult(name = "fechaUltimoAcceso"),
                                        @ColumnResult(name = "movil"),
                                        @ColumnResult(name = "nombres"),
                                        @ColumnResult(name = "apellidos"),
                                        @ColumnResult(name = "numeroDocumento"),
                                        @ColumnResult(name = "paisId"),
                                        @ColumnResult(name = "tipoDocumentoId"),
                                        @ColumnResult(name = "ubigeo")
                                }
                        )
                }),

        @SqlResultSetMapping(name = "resultMappingClienteFitnessDistribucion",
                classes = {
                        @ConstructorResult(
                                targetClass = ClienteFitnessPOJO.class,
                                columns = {
                                        @ColumnResult(name = "id"),
                                        @ColumnResult(name = "tipoCanalVentaId"),
                                        @ColumnResult(name = "condicionAnatomica"),
                                        @ColumnResult(name = "fechaNacimiento"),
                                        @ColumnResult(name = "ubigeo"),
                                        @ColumnResult(name = "sexo"),
                                        @ColumnResult(name = "fechaCreacion"),
                                        @ColumnResult(name = "predeterminadaFichaId")
                                        /*,

                                        @ColumnResult(name = "predeterminadaFichaId")*/
                                }
                        )
                })
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

    @JsonSerialize(using = JsonMoneyDoubleSimpleSerializer.class)
    @Column(precision = 6, scale = 3, nullable = false)
    private Double peso;
    @Column(nullable = false)
    private Integer talla;
    @JsonSerialize(using = JsonMoneyDoubleSimpleSerializer.class)
    @Column(precision = 3, scale = 1, nullable = false)
    private Double imc;
    @Column(nullable = false)
    private Integer nivel;//Nivel del atleta
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<CondicionMejora> mejoras;
    @Column
    private String tiempoUnKilometro;
    @JsonSerialize(using = JsonMoneyDoubleSimpleSerializer.class)
    @Column(precision = 5, scale = 2, nullable = false)
    private Double kilometrajePromedioSemana;
    @Column(nullable = false)
    private Integer diasSemanaCorriendo;
    @Column(nullable = false)
    private boolean flagCalentamiento;
    @Column(nullable = false)
    private boolean flagEstiramiento;
    @Column(nullable = false)
    private String desgasteZapatilla;
    @Column(nullable = true)
    private String desgasteZapatillaOtro;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String desObjetivos;
    @Column(nullable = false)
    private String desTerPredom;

    private String desTerPredomOtro;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private CondicionAnatomica condicionAnatomica;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private Salud salud;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<CompetenciaRunner> competencias;
    @Column(nullable = false)
    private String fitElementos;
    @Column(nullable = false)
    private Integer frecuenciaComunicacion;
    @Column(nullable = false)
    private String tiempoDistancia;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClienteId", referencedColumnName = "SecurityUserId", updatable = false)
    private Cliente cliente;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoCanalVentaId")
    private TipoCanalVenta TipoCanalVenta;

    public ClienteFitness(){}

    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }

    public void setCliente(Integer cliId){
        this.cliente = new Cliente(cliId);
    }

}
