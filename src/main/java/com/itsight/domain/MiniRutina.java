package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.itsight.domain.jsonb.MiRutinaPk;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
})
@NamedEntityGraphs({
        @NamedEntityGraph(name = "miniRutina")
})
public class MiniRutina implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MiniRutinaId")
    private Integer id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TrainerId", referencedColumnName = "SecurityUserId", updatable = false)
    private Trainer trainer;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoriaId", updatable = false)
    private Categoria categoria;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<MiRutinaPk> miRutinaIds;

    public void setTrainer(Trainer trainer){
        this.trainer = trainer;
    }

    public void setTrainer(Integer trainerId){
        this.trainer = new Trainer(trainerId);
    }

    public void setCategoria(Categoria categoria){
        this.categoria= categoria;
    }

    public void setCategoria(int categoriaId){
        this.categoria= new Categoria(categoriaId);
    }

}
