package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itsight.domain.jsonb.CuentaPago;
import com.itsight.domain.pojo.TrainerFichaPOJO;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
                    @ColumnResult(name = "nomUbigeo", type = String.class),
                    @ColumnResult(name = "acerca", type = String.class),
                    @ColumnResult(name = "canPerValoracion", type = Integer.class),
                    @ColumnResult(name = "totalValoracion", type = Double.class),
                    @ColumnResult(name = "nomImgPerfil", type = String.class),
                    @ColumnResult(name = "nomPag", type = String.class),
                    @ColumnResult(name = "tipoTrainerId", type = Integer.class),
                    @ColumnResult(name = "rowz")
                }
            )
        }
    ),
    @SqlResultSetMapping(
            name="getByNomPagParOrTrainerId",
            classes = {
                    @ConstructorResult(
                            targetClass = TrainerFichaPOJO.class,
                            columns = {
                                    @ColumnResult(name = "id", type = Integer.class),
                                    @ColumnResult(name = "nombreCompleto", type = String.class),
                                    @ColumnResult(name = "sexo", type = Integer.class),
                                    @ColumnResult(name = "fichaClienteIds", type = String.class),
                                    @ColumnResult(name = "especialidad", type = String.class),
                                    @ColumnResult(name = "disciplinas", type = String.class),
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
                                    @ColumnResult(name = "horario", type = String.class),
                                    @ColumnResult(name = "miniGaleria", type = String.class),
                                    @ColumnResult(name = "nota", type = String.class),
                                    @ColumnResult(name = "correo", type = String.class),
                                    @ColumnResult(name = "ubigeo", type = String.class),
                                    @ColumnResult(name = "canPerValoracion", type = Integer.class),
                                    @ColumnResult(name = "totalValoracion", type = Double.class),
                                    @ColumnResult(name = "nomImgPerfil", type = String.class),
                                    @ColumnResult(name = "cuentas", type = String.class),
                                    @ColumnResult(name = "mediosPago", type = String.class),
                                    @ColumnResult(name = "mapCoordenadas", type = String.class),
                                    @ColumnResult(name = "mapCircleRadio", type = Double.class),
                                    @ColumnResult(name = "redes", type = String.class),
                                    @ColumnResult(name = "staffGaleria", type = String.class),
                                    @ColumnResult(name = "tipoTrainerId", type = Integer.class),
                                    @ColumnResult(name = "svcIds", type = String.class)
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
                              "\tt.nom_ubigeo nomUbigeo, \n" +
                              "\tf.acerca, \n" +
                              "\tt.can_per_valoracion canPerValoracion, \n" +
                              "\tt.total_valoracion totalValoracion,\n" +
                              "\tCONCAT(f.uuid_fp, f.ext_fp) nomImgPerfil,\n" +
                              "\tf.nom_pag nomPag, \n" +
                              "\tt.tipo_trainer_id tipoTrainerId, \n" +
                              "cast(count(*) over() as int) rowz \n" +
                              "FROM trainer t \n" +
                              "INNER JOIN trainer_ficha f ON t.security_user_id=f.trainer_id \n" +
                              "WHERE t.tipo_trainer_id != 3 AND t.flag_activo = true ORDER BY 1 DESC \n" +
                              "LIMIT ?1 \n" +
                              "OFFSET ?2",
                      resultSetMapping = "getAllByDemo"),
    @NamedNativeQuery(name = "TrainerFicha.findByNomPagPar",
                      query = "SELECT \n" +
                              "\tf.trainer_id id, \n" +
                              "\tt.tipo_trainer_id tipoTrainerId, \n" +
                              "\tCONCAT(t.nombres,' ' ,t.apellidos) nombreCompleto, \n" +
                              "\tt.sexo, \n" +
                              "\tt.ficha_cliente_ids fichaClienteIds,\n" +
                              "\tf.disciplinas, \n" +
                              "\tf.especialidad, \n" +
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
                              "\tf.horario,\n" +
                              "\tf.mini_galeria miniGaleria,\n" +
                              "\tf.nota,\n" +
                              "\tt.correo,\n" +
                              "\tt.ubigeo, \n" +
                              "\tt.can_per_valoracion canPerValoracion, \n" +
                              "\tt.total_valoracion totalValoracion,\n" +
                              "\tCONCAT(f.uuid_fp, f.ext_fp) nomImgPerfil,\n" +
                              "\tCAST(f.cuentas AS text),\n" +
                              "\tf.medios_pago mediosPago,\n" +
                              "\tf.map_coordenadas mapCoordenadas,\n" +
                              "\tf.map_circle_radio mapCircleRadio, \n" +
                              "\tf.redes, \n" +
                              "\tf.staff_galeria staffGaleria, \n" +
                              "\tt.tipo_trainer_id tipoTrainerId, \n" +
                              "\tf.svc_ids svcIds \n" +
                              "FROM trainer t \n" +
                              "INNER JOIN trainer_ficha f ON t.security_user_id=f.trainer_id\n" +
                              "WHERE f.nom_pag = ?",
                      resultSetMapping = "getByNomPagParOrTrainerId"),
        @NamedNativeQuery(name = "TrainerFicha.findByTrainerId",
                      query = "SELECT \n" +
                              "\tf.trainer_id id, \n" +
                              "\tCONCAT(t.nombres,' ' ,t.apellidos) nombreCompleto, \n" +
                              "\tt.sexo, \n" +
                              "\tt.ficha_cliente_ids fichaClienteIds,\n" +
                              "\tf.disciplinas, \n" +
                              "\tf.especialidad, \n" +
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
                              "\tf.horario,\n" +
                              "\tf.mini_galeria miniGaleria,\n" +
                              "\tf.nota,\n" +
                              "\tt.correo,\n" +
                              "\tt.ubigeo, \n" +
                              "\tt.can_per_valoracion canPerValoracion, \n" +
                              "\tt.total_valoracion totalValoracion,\n" +
                              "\tCONCAT(f.uuid_fp, f.ext_fp) nomImgPerfil,\n" +
                              "\tCAST(f.cuentas AS text),\n" +
                              "\tf.medios_pago mediosPago,\n" +
                              "\tf.map_coordenadas mapCoordenadas,\n" +
                              "\tf.map_circle_radio mapCircleRadio, \n" +
                              "\tf.redes, \n" +
                              "\tf.staff_galeria staffGaleria, \n" +
                              "\tt.tipo_trainer_id tipoTrainerId, \n" +
                              "\tf.svc_ids svcIds \n" +
                              "FROM trainer t \n" +
                              "INNER JOIN trainer_ficha f ON t.security_user_id=f.trainer_id\n" +
                              "WHERE t.security_user_id = ?",
                      resultSetMapping = "getByNomPagParOrTrainerId")
})
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "fn_validacion_nom_pag",
                procedureName = "check_nom_pag_existe",
                parameters = {
                        @StoredProcedureParameter(name = "_nom_pag", mode = ParameterMode.IN, type = String.class),
                        @StoredProcedureParameter(name = "result", mode = ParameterMode.OUT, type = Boolean.class)
                })
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
    @Column(nullable = true)
    private String especialidad;
    @Column(nullable = false, columnDefinition="TEXT")
    private String acerca;
    @Column(nullable = false)
    private String idiomas;
    @Column(nullable = true, columnDefinition="TEXT")
    private String estudios;
    @Column(nullable = false, columnDefinition="TEXT")
    private String metodoTrabajo;
    @Column(nullable = true, columnDefinition="TEXT")
    private String experiencias;
    @Column(nullable = false, columnDefinition="TEXT")
    private String disciplinas;
    @Column(nullable = false, columnDefinition="TEXT")
    private String resultados;
    @Column(nullable = false)
    private String niveles;
    @Column(nullable = true)
    private String centroTrabajo;
    @Column(nullable = true)
    private String especialidades;
    @Column(nullable = false)
    private String formasTrabajo;
    @Column(nullable = true)
    private String horario;
    @Column(nullable = true, columnDefinition="TEXT")
    private String miniGaleria;
    @Column(nullable = true, columnDefinition="TEXT")
    private String staffGaleria;
    @Column(nullable = true, columnDefinition="TEXT")
    private String nota;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = true)
    private List<CuentaPago> cuentas;

    @Column(nullable = false)
    private String uuidFp;

    @Column(nullable = false, updatable = false)
    private String extFp;

    @Column(nullable = false, updatable = false)
    private String nomPag;

    @Column(nullable = true)
    private String mediosPago;

    @Column(nullable = true)
    private String mapCoordenadas;
    @Column(nullable = true, precision = 7, scale = 2)
    private Double mapCircleRadio;
    @Column(nullable = true, columnDefinition = "TEXT")
    private String redes;
    @Column(nullable = true)
    private Boolean flagFichaAceptada;
    //Campos de trainer de empresa
    @Column(nullable = true)
    private Integer trEmpId;
    @Column(nullable = true)
    private String svcIds;
    @Column(nullable = false)
    private boolean flagPermisoUpd;

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
