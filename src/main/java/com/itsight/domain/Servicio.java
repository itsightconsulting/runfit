package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itsight.domain.jsonb.Tarifario;
import com.itsight.domain.pojo.ServicioPOJO;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "getAllByTrainerId",
        classes = {
            @ConstructorResult(
                targetClass = ServicioPOJO.class,
                columns = {
                    @ColumnResult(name = "id", type = Integer.class),
                    @ColumnResult(name = "nombre", type = String.class),
                    @ColumnResult(name = "descripcion", type = String.class),
                    @ColumnResult(name = "incluye", type = String.class),
                    @ColumnResult(name = "infoAdicional", type = String.class),
                    @ColumnResult(name = "tarifarios", type = String.class),
                    @ColumnResult(name = "tycFile", type = String.class)
                }
            )
        }
    ),
    @SqlResultSetMapping(name = "resultMappingTopServiciosTrainer",
        classes = {
             @ConstructorResult(
                 targetClass = ServicioPOJO.class,
                 columns = {
                    @ColumnResult(name = "trainerid"),
                    @ColumnResult(name = "trainerNombre"),
                    @ColumnResult(name = "servicioNombre"),
                    @ColumnResult(name = "servicioId"),
                    @ColumnResult(name = "qtyClientes"),
                    @ColumnResult(name = "qtyHombre"),
                    @ColumnResult(name = "qtyMujer")
                 }
                )
                }),

        @SqlResultSetMapping(name = "resultMappingTopServiciosByTrainerId",
                classes = {
                        @ConstructorResult(
                                targetClass = ServicioPOJO.class,
                                columns = {
                                        @ColumnResult(name = "id"),
                                        @ColumnResult(name = "nombre"),
                                        @ColumnResult(name = "qtyClientes"),
                                        @ColumnResult(name = "qtyHombre"),
                                        @ColumnResult(name = "qtyMujer")
                                }
                        )
                })
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "Servicio.findAllByTrainerId",
                query = "SELECT \n" +
                        "\ts.servicio_id id,\n" +
                        "\ts.nombre, \n" +
                        "\ts.descripcion, \n" +
                        "\ts.incluye, \n" +
                        "\ts.info_adicional infoAdicional, \n" +
                        "\tCAST(s.tarifarios AS text) tarifarios,\n" +
                        "\tCONCAT(s.tycUuid,s.tyc_ext) tycFile \n" +
                        "FROM servicio s \n"+
                        "WHERE s.trainer_id = ?1",
                resultSetMapping = "getAllByTrainerId")
})
public class Servicio implements Serializable {

    @Id
    @GeneratedValue(generator = "servicio_seq")
    @GenericGenerator(
            name = "servicio_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @org.hibernate.annotations.Parameter(name = "servicio_seq", value = "servicio_seq"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "ServicioId")
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String incluye;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String infoAdicional;

    @Type(type = "jsonb")
    @Column(nullable = true, columnDefinition = "jsonb")
    private List<Tarifario> tarifarios;

    @Column(nullable = true)
    private UUID tycUUID;

    @Column(nullable = true)
    private String tycExt;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TrainerId", referencedColumnName = "SecurityUserId")
    private Trainer trainer;

    /*@JsonBackReference
    @ManyToMany(mappedBy = "servicios", fetch = FetchType.LAZY)
    private List<Cliente> clientes = new ArrayList<>();*/

    @JsonBackReference
    @OneToMany(
            mappedBy = "servicio",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ClienteServicio> posts = new ArrayList<>();
}
