package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itsight.domain.base.AuditingEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;


@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "fn_validacion_nombre_sub_categoria",
                procedureName = "check_sub_categ_plant_predisen_existe",
                parameters = {
                        @StoredProcedureParameter(name = "_nom_sub_cat", mode = ParameterMode.IN, type = String.class),
                        @StoredProcedureParameter(name = "_cat_id", mode = ParameterMode.IN, type= Integer.class),
                        @StoredProcedureParameter(name = "result", mode = ParameterMode.OUT, type = Boolean.class)
                })})

@Data
@Entity
public class SubCategoriaPlantilla extends AuditingEntity {

    @Id
    @GeneratedValue(generator = "sub_cat_plantilla_seq")
    @GenericGenerator(
            name = "sub_cat_plantilla_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @org.hibernate.annotations.Parameter(name = "sec_user_seq", value = "sec_user_seq"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "SubCategoriaPlantillaId")
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Boolean favorito;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoriaPlantillaId")
    private CategoriaPlantilla categoriaPlantilla;

    @JsonBackReference
    @OneToMany(fetch =  FetchType.LAZY , mappedBy = "subCategoriaPlantilla", orphanRemoval = true)
    private List<RutinaPlantilla> lstRutinaPlantilla;

}
