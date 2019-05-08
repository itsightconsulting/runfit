package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itsight.domain.jsonb.CuentaPago;
import com.itsight.domain.jsonb.Servicio;
import com.itsight.domain.pojo.TrainerFichaPOJO;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "trainerFicha.trainer",
                attributeNodes = {
                        @NamedAttributeNode(value = "trainer")
                }),
        @NamedEntityGraph(name = "trainerFicha")
})
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@SqlResultSetMappings({
    @SqlResultSetMapping(
        name="getAllByDemo",
        classes = {
            @ConstructorResult(
                targetClass = TrainerFichaPOJO.class,
                columns = {
                    @ColumnResult(name = "id", type = Integer.class),
                    @ColumnResult(name = "nombreCompleto", type = String.class),
                    @ColumnResult(name = "especialidad", type = String.class),
                    @ColumnResult(name = "ubigeo", type = String.class),
                    @ColumnResult(name = "acerca", type = String.class),
                    @ColumnResult(name = "canPerValoracion", type = Integer.class),
                    @ColumnResult(name = "totalValoracion", type = Double.class),
                    @ColumnResult(name = "rutaWebImg", type = String.class),
                    @ColumnResult(name = "servicios", type = String.class),
                    @ColumnResult(name = "nomPag", type = String.class)
                }
            )
        }
    ),
    @SqlResultSetMapping(
            name="getByNomPagPar",
            classes = {
                    @ConstructorResult(
                            targetClass = TrainerFichaPOJO.class,
                            columns = {
                                    @ColumnResult(name = "id", type = Integer.class),
                                    @ColumnResult(name = "nombreCompleto", type = String.class),
                                    @ColumnResult(name = "fichaClienteIds", type = String.class),
                                    @ColumnResult(name = "especialidad", type = String.class),
                                    @ColumnResult(name = "disciplina", type = String.class),
                                    @ColumnResult(name = "acerca", type = String.class),
                                    @ColumnResult(name = "idiomas", type = String.class),
                                    @ColumnResult(name = "estudios", type = String.class),
                                    @ColumnResult(name = "metodoTrabajo", type = String.class),
                                    @ColumnResult(name = "experiencias", type = String.class),
                                    @ColumnResult(name = "resultados", type = String.class),
                                    @ColumnResult(name = "niveles", type = String.class),
                                    @ColumnResult(name = "centroTrabajo", type = String.class),
                                    @ColumnResult(name = "especialidades", type = String.class),
                                    @ColumnResult(name = "formasTrabajo", type = String.class),
                                    @ColumnResult(name = "miniGaleria", type = String.class),
                                    @ColumnResult(name = "adicionalInfo", type = String.class),
                                    @ColumnResult(name = "correo", type = String.class),
                                    @ColumnResult(name = "ubigeo", type = String.class),
                                    @ColumnResult(name = "canPerValoracion", type = Integer.class),
                                    @ColumnResult(name = "totalValoracion", type = Double.class),
                                    @ColumnResult(name = "rutaWebImg", type = String.class),
                                    @ColumnResult(name = "servicios", type = String.class),
                                    @ColumnResult(name = "cuentas", type = String.class),
                                    @ColumnResult(name = "mapCoordenadas", type = String.class),
                                    @ColumnResult(name = "mapCircleRadio", type = String.class)
                            }
                    )
            }
    )
})
@NamedNativeQueries({
    @NamedNativeQuery(name = "TrainerFicha.findAllWithFgEnt",
                      query = "SELECT \n" +
                              "\tf.trainer_id id,\n" +
                              "\tCONCAT(t.nombres,' ' ,t.apellidos) nombreCompleto, \n" +
                              "\tf.especialidad, \n" +
                              "\tt.ubigeo, \n" +
                              "\tf.acerca, \n" +
                              "\tt.can_per_valoracion canPerValoracion, \n" +
                              "\tt.total_valoracion totalValoracion,\n" +
                              "\tf.ruta_web_img rutaWebImg,\n" +
                              "\tCAST(f.servicios AS text),\n" +
                              "\tf.nom_pag nomPag \n" +
                              "FROM trainer t \n" +
                              "INNER JOIN trainer_ficha f ON t.security_user_id=f.trainer_id ORDER BY 1 DESC\n",
                      resultSetMapping = "getAllByDemo"),
    @NamedNativeQuery(name = "TrainerFicha.findByNomPagPar",
                      query = "SELECT \n" +
                              "\tf.trainer_id id, \n" +
                              "\tCONCAT(t.nombres,' ' ,t.apellidos) nombreCompleto, \n" +
                              "\tt.ficha_cliente_ids fichaClienteIds,\n" +
                              "\tf.especialidad, \n" +
                              "\td.nombre disciplina,\n" +
                              "\tf.acerca,\n" +
                              "\tf.idiomas,\n" +
                              "\tf.estudios,\n" +
                              "\tf.metodo_trabajo metodoTrabajo,\n" +
                              "\tf.experiencias,\n" +
                              "\tf.resultados,\n" +
                              "\tf.niveles,\n" +
                              "\tf.centro_trabajo centroTrabajo,\n" +
                              "\tf.especialidades,\n" +
                              "\tf.formas_trabajo formasTrabajo,\n" +
                              "\tf.mini_galeria miniGaleria,\n" +
                              "\tf.adicional_info adicionalInfo,\n" +
                              "\tt.correo,\n" +
                              "\tt.ubigeo, \n" +
                              "\tt.can_per_valoracion canPerValoracion, \n" +
                              "\tt.total_valoracion totalValoracion,\n" +
                              "\tf.ruta_web_img rutaWebImg,\n" +
                              "\tCAST(f.servicios AS text),\n" +
                              "\tCAST(f.cuentas AS text),\n" +
                              "\tf.map_coordenadas mapCoordenadas,\n" +
                              "\tf.map_circle_radio mapCircleRadio \n" +
                              "FROM trainer t \n" +
                              "INNER JOIN trainer_ficha f ON t.security_user_id=f.trainer_id\n" +
                              "INNER JOIN disciplina d ON d.disciplina_id=f.disciplina_id\n" +
                              "WHERE f.nom_pag = ?",
                      resultSetMapping = "getByNomPagPar")
})
@Entity
@Data
public class TrainerFicha implements Serializable {

    @Id
    @GeneratedValue(generator = "trainer_ficha_seq")
    @GenericGenerator(
            name = "trainer_ficha_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @org.hibernate.annotations.Parameter(name = "trainer_ficha_seq", value = "trainer_ficha_seq"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            })
    @Column(name="TrainerFichaId")
    private Integer id;
    @Column(nullable = false)
    private String especialidad;
    @Column(nullable = false)
    private Integer disciplinaId;
    @Column(nullable = false, columnDefinition="TEXT")
    private String acerca;
    @Column(nullable = false)
    private String idiomas;
    @Column(nullable = false, columnDefinition="TEXT")
    private String estudios;
    @Column(nullable = false, columnDefinition="TEXT")
    private String metodoTrabajo;
    @Column(nullable = false, columnDefinition="TEXT")
    private String experiencias;
    @Column(nullable = false, columnDefinition="TEXT")
    private String resultados;
    @Column(nullable = false)
    private String niveles;
    @Column(nullable = false)
    private String centroTrabajo;
    @Column(nullable = false)
    private String especialidades;
    @Column(nullable = false)
    private String formasTrabajo;
    @Column(nullable = true, columnDefinition="TEXT")
    private String miniGaleria;
    @Column(nullable = true, columnDefinition="TEXT")
    private String adicionalInfo;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = true)
    private List<Servicio> servicios;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = true)
    private List<CuentaPago> cuentas;

    @Column(nullable = true)
    private UUID uuid;

    @Column(nullable = true)
    private String rutaWebImg;

    @Column(nullable = true)
    private String rutaRealImg;

    @Column(nullable = true, unique = true)
    private String nomPag;

    @Column(nullable = true)
    private String mapCoordenadas;
    @Column(nullable = true)
    private String mapCircleRadio;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TrainerId", referencedColumnName = "SecurityUserId", updatable = false)
    private Trainer trainer;

    public TrainerFicha() {
        // TODO Auto-generated constructor stub
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public void setTrainer(Integer trainerId){
        this.trainer = new Trainer(trainerId);
    }

}
