package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.itsight.domain.jsonb.DiaRutinarioPk;
import com.itsight.domain.jsonb.ListaPlantillaSimplePk;
import com.itsight.util.EntityVisitor;
import com.itsight.util.Identifiable;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
})
@NamedEntityGraphs({
    @NamedEntityGraph(name = "miniplantilla", attributeNodes = {

    })
})
public class MiniPlantilla implements Identifiable {

    public static EntityVisitor<MiniPlantilla, EspecificacionSubCategoria> ENTITY_VISITOR = new EntityVisitor<MiniPlantilla, EspecificacionSubCategoria>(MiniPlantilla.class) {
        @Override
        public EspecificacionSubCategoria getParent(MiniPlantilla visitingObject) {
            return visitingObject.getEspecificacionSubCategoria();
        }

        @Override
        public List<MiniPlantilla> getChildren(EspecificacionSubCategoria parent) {
            return parent.getLstMiniPlantilla();
        }

        @Override
        public void setChildren(EspecificacionSubCategoria parent) {
            parent.setLstMiniPlantilla(new ArrayList<>());
        }
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MiniPlantillaId")
    private Integer id;


    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TrainerId", referencedColumnName = "SecurityUserId", updatable = false)
    private Trainer trainer;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EspecificacionSubCategoriaId", updatable = false)
    private EspecificacionSubCategoria especificacionSubCategoria;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<DiaRutinarioPk> diaRutinarioIds;

}
