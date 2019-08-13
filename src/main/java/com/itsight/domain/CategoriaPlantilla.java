package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itsight.domain.base.AuditingEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Data
@Entity
public class CategoriaPlantilla extends AuditingEntity {

  @Id
  @GeneratedValue(generator = "cat_plantilla_seq")
  @GenericGenerator(
          name = "cat_plantilla_seq",
          strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
          parameters = {
                  @org.hibernate.annotations.Parameter(name = "prefer_sequence_per_entity", value = "true"),
                  @org.hibernate.annotations.Parameter(name = "sec_user_seq", value = "sec_user_seq"),
                  @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                  @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
          }
  )
  @Column(name = "CategoriaPlantillaId")
  private Integer id;
  @Column(nullable = false)
  private int tipo;
  @Column(nullable = false)
  private String nombre;

  @Column(nullable = false)
  private Boolean favorito;

  @JsonManagedReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "TrainerId", referencedColumnName ="SecurityUserId")
  private Trainer trainer;

  @JsonBackReference
  @OneToMany(fetch =  FetchType.LAZY , mappedBy = "categoriaPlantilla")
  private List<RutinaPlantilla> lstRutinaPlantilla;

}
