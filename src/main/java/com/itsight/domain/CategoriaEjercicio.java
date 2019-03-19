package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itsight.util.EntityVisitor;
import com.itsight.util.Identifiable;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "categoriaEjercicio"),
})
@Data
public class CategoriaEjercicio implements Identifiable {

    public static EntityVisitor<CategoriaEjercicio, BagForest> ENTITY_VISITOR = new EntityVisitor<CategoriaEjercicio, BagForest>(CategoriaEjercicio.class) {

        @Override
        public BagForest getParent(CategoriaEjercicio visitingObject) {
            return visitingObject.getForest();
        }

        @Override
        public List<CategoriaEjercicio> getChildren(BagForest parent) {
            return parent.getTrees();
        }

        @Override
        public void setChildren(BagForest parent) {
            parent.setTrees(new ArrayList<>());
        }
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoriaEjercicioId")
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = true)
    private String rutaWeb;

    @Column(nullable = true)
    private String rutaReal;

    @Column(nullable = true)
    private UUID uuid;


    @Column(nullable = false)
    private boolean flagActivo;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoriaEjercicio")
    private List<SubCategoriaEjercicio> lstSubCategoriaEjercicio;

    @JsonBackReference
    @ManyToOne
    public BagForest forest;

    public CategoriaEjercicio() {
    }

    public CategoriaEjercicio(Integer id) {
        this.id = id;
    }

    public CategoriaEjercicio(String nombre) {
        this.nombre = nombre;
    }

    public CategoriaEjercicio(String nombre, int forestId) {
        this.nombre = nombre;
        this.forest = new BagForest(forestId);
    }

    public CategoriaEjercicio(String nombre, int forestId, boolean flagActivo, String rutaWeb, String rutaReal, UUID uuid) {
        this.nombre = nombre;
        this.forest = new BagForest(forestId);
        this.flagActivo = flagActivo;
        this.rutaWeb = rutaWeb;
        this.rutaReal = rutaReal;
        this.uuid = uuid;
    }

    public CategoriaEjercicio(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public CategoriaEjercicio(String nombre, boolean flagActivo) {
        this.nombre = nombre;
        this.flagActivo = flagActivo;
    }

    public void setForest(BagForest forest){
        this.forest = forest;
    }

    public void setForest(int bagForestId){
        this.forest = new BagForest(bagForestId);
    }

}
